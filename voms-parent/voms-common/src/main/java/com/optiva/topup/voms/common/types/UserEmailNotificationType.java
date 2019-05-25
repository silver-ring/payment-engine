package com.optiva.topup.voms.common.types;

import org.apache.commons.text.WordUtils;

public enum UserEmailNotificationType {

  DELETE_USER_ACCOUNT,
  UPDATE_USER_ROLES,
  CHANGE_PASSWORD,
  RESET_USER_PASSWORD,
  CHANGE_USER_ACCOUNT_STATUS,
  USER_REGISTRATION;

  public static String getTokenParameterName() {
    return "token";
  }

  public String getTemplate() {
    return this.name().toLowerCase() + ".ftl";
  }

  public String getSubject() {
    return this.name().replace("_", " ") + " NOTIFICATION";
  }

  public String getConfirmationLink(String token) {
    return WordUtils.capitalizeFully(this.name(), '_').replace("_", "") + "Confirmation" + "/" + token;
  }

}
