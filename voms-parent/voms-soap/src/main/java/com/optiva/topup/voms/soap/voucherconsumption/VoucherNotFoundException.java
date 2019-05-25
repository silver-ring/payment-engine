package com.optiva.topup.voms.soap.voucherconsumption;

import com.optiva.topup.voms.common.exceptions.VomsException;

public class VoucherNotFoundException extends VomsException {

  public VoucherNotFoundException() {
    super("Voucher Not Found");
  }

}
