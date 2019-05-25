package com.optiva.topup.voms.batch.scheduledbatch.voucherpolicy.deletedvoucherdestroyingpolicy;

import com.optiva.topup.voms.common.exceptions.VomsException;

public class VoucherArchiveWriteOpenException extends VomsException {

  public VoucherArchiveWriteOpenException(Exception ex) {
    super("Voucher Archive Write Open Error", ex);
  }

}
