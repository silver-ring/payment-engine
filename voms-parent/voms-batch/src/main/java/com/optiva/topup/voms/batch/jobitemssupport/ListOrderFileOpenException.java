package com.optiva.topup.voms.batch.jobitemssupport;

import com.optiva.topup.voms.common.exceptions.VomsException;

public class ListOrderFileOpenException extends VomsException {

  public ListOrderFileOpenException(Exception ex) {
    super("Error Open List Order File", ex);
  }

}
