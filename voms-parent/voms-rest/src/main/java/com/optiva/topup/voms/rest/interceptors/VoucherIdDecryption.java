package com.optiva.topup.voms.rest.interceptors;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface VoucherIdDecryption {

  /**
   * value for voucher id property name which will be decrypted.
   */
  String value() default "voucherId";

}
