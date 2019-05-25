package com.optiva.topup.voms.common.listeners;

import com.optiva.topup.voms.common.auditing.UserActivityAudit;
import com.optiva.topup.voms.common.types.UserActivityType;
import com.optiva.topup.voms.common.utils.ApplicationContextUtil;
import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;

public class AuditEntityListener {

  @PostPersist
  void onPostPersist(Object entity) {
    sendAuditMessage(entity, UserActivityType.CREATE);
  }

  @PostUpdate
  public void onPostUpdate(Object entity) {
    sendAuditMessage(entity, UserActivityType.UPDATE);
  }

  @PostRemove
  public void onPostRemove(Object entity) {
    sendAuditMessage(entity, UserActivityType.DELETE);
  }

  private void sendAuditMessage(Object entity, UserActivityType userActivityType) {
    UserActivityAudit userActivityAudit = ApplicationContextUtil.getApplicationContext()
        .getBean(UserActivityAudit.class);
    userActivityAudit.recordDataActivity(entity, userActivityType);
  }

}
