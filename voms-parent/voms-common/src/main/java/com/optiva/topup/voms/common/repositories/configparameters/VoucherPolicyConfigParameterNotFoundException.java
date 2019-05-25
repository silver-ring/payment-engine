package com.optiva.topup.voms.common.repositories.configparameters;

import com.optiva.topup.voms.common.exceptions.VomsException;

public class VoucherPolicyConfigParameterNotFoundException extends VomsException {

  public VoucherPolicyConfigParameterNotFoundException() {
    super("Voucher Policy Config Parameter Not Found Exception");
  }

}
