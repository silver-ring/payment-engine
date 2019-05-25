package com.optiva.topup.voms.common.auditing;

import com.optiva.topup.voms.common.types.UserActivityType;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
@interface AuditUserActivity {

  /**
   * User activity type value {@link UserActivityType}.
   */
  UserActivityType value();

}
