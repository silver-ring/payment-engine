package com.optiva.topup.voms.integration.util;

import java.util.Map;
import lombok.Getter;

@Getter
public class NotificationEmail {

  private final String subject;
  private final String fromEmail;
  private final String toEmail;
  private final String templateName;
  private final Map<String, Object> parameters;

  public NotificationEmail(String subject, String fromEmail, String toEmail, String templateName,
      Map<String, Object> parameters) {
    this.subject = subject;
    this.fromEmail = fromEmail;
    this.toEmail = toEmail;
    this.templateName = templateName;
    this.parameters = parameters;
  }

}
