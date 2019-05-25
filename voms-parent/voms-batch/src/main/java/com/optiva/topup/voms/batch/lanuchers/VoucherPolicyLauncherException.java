package com.optiva.topup.voms.batch.lanuchers;

import com.optiva.topup.voms.common.exceptions.VomsException;

public class VoucherPolicyLauncherException extends VomsException {

  public VoucherPolicyLauncherException(Exception ex) {
    super("Error Execute Voucher Policy", ex);
  }

}
