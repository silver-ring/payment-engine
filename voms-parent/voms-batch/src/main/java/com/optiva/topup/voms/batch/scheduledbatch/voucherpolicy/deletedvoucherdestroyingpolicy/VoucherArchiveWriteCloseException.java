package com.optiva.topup.voms.batch.scheduledbatch.voucherpolicy.deletedvoucherdestroyingpolicy;

import com.optiva.topup.voms.common.exceptions.VomsException;

public class VoucherArchiveWriteCloseException extends VomsException {

  public VoucherArchiveWriteCloseException(Exception ex) {
    super("Voucher Archive Write Close Error", ex);
  }

}
