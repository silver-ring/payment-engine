package com.optiva.topup.voms.common.repositories.configparameters;

import com.optiva.topup.voms.common.exceptions.VomsException;

public class FileManagerConfigParameterNotFoundException extends VomsException {

  public FileManagerConfigParameterNotFoundException() {
    super("File Manager Config Parameter Not Found Exception");
  }

}
