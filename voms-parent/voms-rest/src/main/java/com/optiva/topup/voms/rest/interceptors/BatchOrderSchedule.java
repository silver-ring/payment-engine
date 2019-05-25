package com.optiva.topup.voms.rest.interceptors;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface BatchOrderSchedule {

  /**
   * The value for schedule topic name.
   */
  String value();

}
