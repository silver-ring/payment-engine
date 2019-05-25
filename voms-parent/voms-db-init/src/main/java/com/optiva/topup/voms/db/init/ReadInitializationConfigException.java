package com.optiva.topup.voms.db.init;

import com.optiva.topup.voms.common.exceptions.VomsException;

public class ReadInitializationConfigException extends VomsException {

  public ReadInitializationConfigException(Exception ex) {
    super("Read Initialization Config Error", ex);
  }

}
