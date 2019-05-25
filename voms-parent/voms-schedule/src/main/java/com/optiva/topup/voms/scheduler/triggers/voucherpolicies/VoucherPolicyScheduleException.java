package com.optiva.topup.voms.scheduler.triggers.voucherpolicies;

import com.optiva.topup.voms.common.exceptions.VomsException;

public class VoucherPolicyScheduleException extends VomsException {

  public VoucherPolicyScheduleException(Exception ex) {
    super("Voucher Policy Schedule Exception", ex);
  }


}
