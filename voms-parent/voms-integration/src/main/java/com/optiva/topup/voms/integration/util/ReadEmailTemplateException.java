package com.optiva.topup.voms.integration.util;

import com.optiva.topup.voms.common.exceptions.VomsException;

public class ReadEmailTemplateException extends VomsException {

  public ReadEmailTemplateException(Exception ex) {
    super("Unable to read email template", ex);
  }

}
