package com.optiva.topup.voms.common.auditing;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Aspect
@Configuration
public class AuditUserActivityInterceptor {

  private final UserActivityAudit userActivityAudit;

  @Autowired
  public AuditUserActivityInterceptor(UserActivityAudit userActivityAudit) {
    this.userActivityAudit = userActivityAudit;
  }

  @AfterReturning(pointcut = "@annotation(auditUserActivity)", returning = "obj")
  public void sendBatchOrderScheduleMessage(AuditUserActivity auditUserActivity, Object obj) {
    userActivityAudit.recordDataActivity(obj, auditUserActivity.value());
  }

}
