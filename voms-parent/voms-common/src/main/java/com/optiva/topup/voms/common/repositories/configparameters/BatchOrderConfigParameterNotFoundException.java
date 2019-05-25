package com.optiva.topup.voms.common.repositories.configparameters;

import com.optiva.topup.voms.common.exceptions.VomsException;

public class BatchOrderConfigParameterNotFoundException extends VomsException {

  public BatchOrderConfigParameterNotFoundException() {
    super("Batch Order Config Parameter Not Found Exception");
  }

}
