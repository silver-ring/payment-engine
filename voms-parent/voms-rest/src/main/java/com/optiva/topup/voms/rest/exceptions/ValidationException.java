package com.optiva.topup.voms.rest.exceptions;

public class ValidationException extends RuntimeException {

  private static final long serialVersionUID = 3801504878110173884L;

  public ValidationException(String message) {
    super(message);
  }

}
