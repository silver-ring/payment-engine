package com.optiva.topup.voms.common.repositories.configparameters;

import com.optiva.topup.voms.common.exceptions.VomsException;

public class EmailConfigParameterNotFoundException extends VomsException {

  public EmailConfigParameterNotFoundException() {
    super("Email Config Parameter Not Found Exception");
  }

}
