package com.optiva.topup.voms.rest.resources.changepasswordconfirmation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
class ChangePasswordConfirmationDto {

  private String changePasswordToken;
  private String newPassword;

}
