package com.optiva.topup.voms.common.repositories;

import com.optiva.topup.voms.common.exceptions.VomsException;

public class TemporaryFileNotFoundException extends VomsException {

  public TemporaryFileNotFoundException() {
    super("Temporary File Not Found Exception");
  }
}
