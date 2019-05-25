package com.optiva.topup.voms.common.utils;

import com.optiva.topup.voms.common.exceptions.VomsException;

public class FileCreationException extends VomsException {

  public FileCreationException(Exception ex) {
    super("File Creation Error", ex);
  }

}
