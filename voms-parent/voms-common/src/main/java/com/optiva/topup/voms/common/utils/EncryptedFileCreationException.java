package com.optiva.topup.voms.common.utils;

import com.optiva.topup.voms.common.exceptions.VomsException;

public class EncryptedFileCreationException extends VomsException {

  public EncryptedFileCreationException(Exception ex) {
    super("Encrypted File Creation Error", ex);
  }

}
