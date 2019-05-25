package com.optiva.topup.voms.batch.lanuchers;

import com.optiva.topup.voms.common.exceptions.VomsException;

public class BatchOrderLauncherException extends VomsException {

  public BatchOrderLauncherException(Exception ex) {
    super("Batch Order Launch Error", ex);
  }

}
