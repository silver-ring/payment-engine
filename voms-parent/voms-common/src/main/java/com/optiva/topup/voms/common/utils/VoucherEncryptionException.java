package com.optiva.topup.voms.common.utils;

import com.optiva.topup.voms.common.exceptions.VomsException;

public class VoucherEncryptionException extends VomsException {

  public VoucherEncryptionException(Exception ex) {
    super("Voucher Encryption Error", ex);
  }

}
