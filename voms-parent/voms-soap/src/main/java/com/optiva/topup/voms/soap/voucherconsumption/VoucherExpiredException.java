package com.optiva.topup.voms.soap.voucherconsumption;

import com.optiva.topup.voms.common.exceptions.VomsException;

public class VoucherExpiredException extends VomsException {

  public VoucherExpiredException() {
    super("Voucher Expired");
  }

}
