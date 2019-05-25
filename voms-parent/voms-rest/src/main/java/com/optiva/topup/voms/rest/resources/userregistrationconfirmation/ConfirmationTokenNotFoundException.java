package com.optiva.topup.voms.rest.resources.userregistrationconfirmation;

import com.optiva.topup.voms.common.exceptions.VomsException;

public class ConfirmationTokenNotFoundException extends VomsException {

  public ConfirmationTokenNotFoundException() {
    super("Confirmation Token Not Found Exception");
  }

}
