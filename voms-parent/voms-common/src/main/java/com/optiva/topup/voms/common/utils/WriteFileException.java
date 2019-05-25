package com.optiva.topup.voms.common.utils;

import com.optiva.topup.voms.common.exceptions.VomsException;

public class WriteFileException extends VomsException {

  public WriteFileException(Exception ex) {
    super("Write File Error", ex);
  }

}
