package com.optiva.topup.voms.rest.interceptors;

import com.optiva.topup.voms.common.exceptions.VomsException;

public class DecryptVoucherIdException extends VomsException {

  public DecryptVoucherIdException(Exception ex) {
    super("Decrypt Voucher Id Exception", ex);
  }

}
