package com.optiva.topup.voms.rest.resources.resetuserpasswordconfirmation;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResetUserPasswordConfirmationDto {

  private String resetUserPasswordToken;
  private String newPassword;

}
