package com.optiva.topup.voms.db.init.utils;

import com.optiva.topup.voms.common.exceptions.VomsException;

public class InvalidSqlScriptException extends VomsException {

  public InvalidSqlScriptException(Exception ex) {
    super("Invalid Sql Script Exception", ex);
  }

}
