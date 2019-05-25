package com.optiva.topup.voms.common.utils;

import com.optiva.topup.voms.common.exceptions.VomsException;

public class FileOpenException extends VomsException {

  public FileOpenException(Exception ex) {
    super("File Open Error", ex);
  }

}
