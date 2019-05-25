package com.optiva.topup.voms.common.repositories.configparameters;

import com.optiva.topup.voms.common.exceptions.VomsException;

public class UserManagementConfigParameterNotFoundException extends VomsException {

  public UserManagementConfigParameterNotFoundException() {
    super("User Management Config Parameter Not Found Exception");
  }

}
