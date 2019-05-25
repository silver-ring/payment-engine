package com.optiva.topup.voms.common.utils;

import com.optiva.topup.voms.common.exceptions.VomsException;

public class VoucherEncryptionInitializationException extends VomsException {

  public VoucherEncryptionInitializationException(Exception ex) {
    super("Voucher Encryption Initialization Error", ex);
  }

}
