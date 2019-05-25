package com.optiva.topup.voms.rest.interceptors;

import com.optiva.topup.voms.common.exceptions.VomsException;

public class EncryptVoucherIdException extends VomsException {

  public EncryptVoucherIdException(Throwable ex) {
    super("Encrypt Voucher Id Exception", ex);
  }

}
