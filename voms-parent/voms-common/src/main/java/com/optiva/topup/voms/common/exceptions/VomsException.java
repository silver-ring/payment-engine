package com.optiva.topup.voms.common.exceptions;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class VomsException extends RuntimeException {

  private static final long serialVersionUID = -5983319769394291373L;

  protected VomsException() {
    super("Unknown Error");
    log.error(this);
  }

  protected VomsException(String message) {
    super(message);
    log.error(this);
  }

  protected VomsException(String message, Throwable ex) {
    super(message, ex);
    log.error(this);
  }

  protected VomsException(Exception ex) {
    super("Unknown Error", ex);
    log.error(this);
  }

}
