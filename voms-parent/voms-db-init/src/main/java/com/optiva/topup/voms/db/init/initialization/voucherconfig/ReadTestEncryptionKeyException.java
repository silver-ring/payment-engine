package com.optiva.topup.voms.db.init.initialization.voucherconfig;

import com.optiva.topup.voms.common.exceptions.VomsException;

public class ReadTestEncryptionKeyException extends VomsException {

  public ReadTestEncryptionKeyException(Exception ex) {
    super("Read Test Encryption Key Exception", ex);
  }

}
