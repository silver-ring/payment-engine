package com.optiva.topup.voms.common.utils;

import com.optiva.topup.voms.common.exceptions.VomsException;

public class ReadResourceFileException extends VomsException {

  public ReadResourceFileException(Exception ex) {
    super("Read Resource File Error", ex);
  }

}
