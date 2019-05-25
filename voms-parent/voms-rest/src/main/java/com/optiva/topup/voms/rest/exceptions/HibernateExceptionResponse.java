package com.optiva.topup.voms.rest.exceptions;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HibernateExceptionResponse {

  private String message;
  private String errorCode;
  private String sql;
  private String violationType;
  private String constraintName;
  private List<String> values;
  private String id;
  private List<String> props;
  private String tableName;

}
