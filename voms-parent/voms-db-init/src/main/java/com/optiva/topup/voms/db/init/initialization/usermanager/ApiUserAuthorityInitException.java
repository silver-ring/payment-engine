package com.optiva.topup.voms.db.init.initialization.usermanager;

import com.optiva.topup.voms.common.exceptions.VomsException;

public class ApiUserAuthorityInitException extends VomsException {

  public ApiUserAuthorityInitException(Exception ex) {
    super("Api User Authority Initialization Exception", ex);
  }

}
