package com.optiva.topup.voms.common.utils;

import com.optiva.topup.voms.common.exceptions.VomsException;

public class ReadPublicKeyException extends VomsException {

  public ReadPublicKeyException(Exception ex) {
    super("Error During Read Public Key", ex);
  }

  public ReadPublicKeyException() {
    super("Can't Read Public Key");
  }

}
