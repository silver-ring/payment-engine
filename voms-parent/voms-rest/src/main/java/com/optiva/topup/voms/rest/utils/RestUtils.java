package com.optiva.topup.voms.rest.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import lombok.extern.log4j.Log4j2;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.core.EmbeddedWrapper;
import org.springframework.hateoas.server.core.EmbeddedWrappers;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class RestUtils {

  public List<Integer> extractIdsFromLinks(List<String> links) {
    List<Integer> result = new ArrayList<>();
    links.forEach(link -> {
      try {
        int idStartIndex = link.lastIndexOf("/");
        Integer id = Integer.parseInt(link.substring(idStartIndex + 1));
        result.add(id);
      } catch (Exception ex) {
        log.error(ex);
      }
    });
    return result;
  }

  public CollectionModel<Object> getResources(List<?> resourcesList, Class<?> resourceClass) {
    CollectionModel<Object> objectResource;
    if (resourcesList.isEmpty()) {
      EmbeddedWrappers wrappers = new EmbeddedWrappers(false);
      EmbeddedWrapper wrapper = wrappers.emptyCollectionOf(resourceClass);
      objectResource = new CollectionModel<>(Collections.singletonList(wrapper));
    } else {
      EmbeddedWrappers wrappers = new EmbeddedWrappers(false);
      EmbeddedWrapper wrapper = wrappers.wrap(resourcesList);
      objectResource = new CollectionModel<>(Collections.singletonList(wrapper));
    }
    return objectResource;
  }

}
