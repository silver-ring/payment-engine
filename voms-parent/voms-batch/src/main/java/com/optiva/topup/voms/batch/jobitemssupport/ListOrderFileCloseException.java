package com.optiva.topup.voms.batch.jobitemssupport;

import com.optiva.topup.voms.common.exceptions.VomsException;

public class ListOrderFileCloseException extends VomsException {

  public ListOrderFileCloseException(Exception ex) {
    super("Error Close List Order File", ex);
  }

}
