package com.optiva.topup.voms.scheduler.triggers.voucherpolicies;

import com.optiva.topup.voms.common.exceptions.VomsException;

public class VoucherPolicyRescheduleException extends VomsException {

  public VoucherPolicyRescheduleException(Exception ex) {
    super("Voucher Policy Reschedule Exception", ex);
  }

}
