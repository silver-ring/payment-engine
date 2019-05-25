package com.optiva.topup.voms.rest.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
class PipelineObject {

  private HibernateExceptionResponse hibernateExceptionResponse;
  private String nextPart;

}

