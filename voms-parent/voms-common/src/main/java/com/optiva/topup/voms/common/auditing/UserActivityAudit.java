package com.optiva.topup.voms.common.auditing;

import com.optiva.topup.voms.common.document.UserActivity;
import com.optiva.topup.voms.common.support.WithDocumentTopicsSupport;
import com.optiva.topup.voms.common.types.UserActivityType;
import com.optiva.topup.voms.common.utils.DateUtils;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.transaction.Transactional;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.beanutils.PropertyUtils;
import org.elasticsearch.common.UUIDs;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class UserActivityAudit implements WithDocumentTopicsSupport {

  @Transactional
  public void recordDataActivity(Object dataObject, UserActivityType userActivityType) {
    String email = getCurrentEmail();

    if (email == null || dataObject == null) {
      return;
    }
    Map<String, Object> data = getData(dataObject);

    String objectName = dataObject.getClass().getSimpleName();

    UserActivity userActivity = new UserActivity();
    userActivity.setId(UUIDs.randomBase64UUID());
    userActivity.setUserActivityType(userActivityType);
    userActivity.setData(data);
    userActivity.setTarget(objectName);
    userActivity.setEventTimestamp(LocalDateTime.now());
    userActivity.setEmail(email);

    sendDocumentMessage(userActivity);
  }

  private String getCurrentEmail() {
    return Optional.ofNullable(SecurityContextHolder.getContext()).map(SecurityContext::getAuthentication)
        .filter(Authentication::isAuthenticated)
        .map(Authentication::getPrincipal)
        .map(o -> {
          if (o instanceof String) {
            return (String) o;
          } else if (o instanceof User) {
            return ((User) o).getUsername();
          } else {
            return "Unknown";
          }
        }).orElse(null);
  }

  private Map<String, Object> getData(Object obj) {
    Map<String, Object> data = new HashMap<>();
    try {
      PropertyUtils.describe(obj).forEach((key, value) -> {
        if (value == null) {
          data.put(key, null);
        } else if (value.getClass().equals(Class.class)) {
          return;
        } else if (isDataObject(value)) {
          data.put(key, getDataObjInfo(value));
        } else if (isCollectionObject(value)) {
          data.put(key, getCollectionData(value));
        } else if (isDateObject(value)) {
          data.put(key, getDateObjInfo(value));
        } else if (isDateTimeObject(value)) {
          data.put(key, getDateTimeObjInfo(value));
        } else {
          data.put(key, value);
        }
      });
    } catch (Exception ex) {
      log.error(ex);
    }
    return data;
  }

  private List<Object> getCollectionData(Object value) {
    Collection<?> collectionValue = (Collection<?>) value;
    List<Object> values = new ArrayList<>();
    collectionValue.forEach((item) -> {
      if (isDataObject(item)) {
        values.add(getDataObjInfo(item));
      } else {
        values.add(item);
      }
    });
    return values;
  }

  private Object getDataObjInfo(Object obj) {
    Map<String, Object> data = new HashMap<>();
    try {
      PropertyUtils.describe(obj).forEach((key, value) -> {
        if (value == null) {
          data.put(key, null);
        } else if (value.getClass().equals(Class.class)) {
          return;
        } else if (isDataObject(value)) {
          return;
        } else if (isCollectionObject(value)) {
          return;
        } else if (isDateObject(value)) {
          data.put(key, getDateObjInfo(value));
        } else if (isDateTimeObject(value)) {
          data.put(key, getDateTimeObjInfo(value));
        } else {
          data.put(key, value);
        }
      });
    } catch (Exception ex) {
      log.error(ex);
    }
    return data;
  }

  private boolean isDataObject(Object obj) {
    return obj.getClass().isAnnotationPresent(Entity.class) || obj.getClass()
        .isAnnotationPresent(Document.class) || obj.getClass().isAnnotationPresent(Embeddable.class);
  }

  private Object getDateTimeObjInfo(Object obj) {
    return ((LocalDateTime) obj).format(DateTimeFormatter.ofPattern(DateUtils.DATE_TIME_FORMAT));
  }

  private Object getDateObjInfo(Object obj) {
    return ((LocalDate) obj).format(DateTimeFormatter.ofPattern(DateUtils.DATE_FORMAT));
  }

  private boolean isCollectionObject(Object obj) {
    return Collection.class.isAssignableFrom(obj.getClass());
  }

  private boolean isDateObject(Object obj) {
    return LocalDate.class.isAssignableFrom(obj.getClass());
  }

  private boolean isDateTimeObject(Object obj) {
    return LocalDateTime.class.isAssignableFrom(obj.getClass());
  }

}
