package com.optiva.topup.voms.rest.exceptions;

import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ValidationExceptionHandler {

  @ExceptionHandler(ValidationException.class)
  public void handleBadRequests(HttpServletResponse response, ValidationException validationException)
      throws IOException {
    response.sendError(HttpStatus.BAD_REQUEST.value(), validationException.getMessage());
  }

}
