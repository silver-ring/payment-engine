package com.optiva.topup.voms.rest.interceptors;

import com.optiva.topup.voms.common.exceptions.VomsException;

public class ReadingVoucherIdException extends VomsException {

  public ReadingVoucherIdException(Exception ex) {
    super("Reading Voucher Id Error", ex);
  }

}
