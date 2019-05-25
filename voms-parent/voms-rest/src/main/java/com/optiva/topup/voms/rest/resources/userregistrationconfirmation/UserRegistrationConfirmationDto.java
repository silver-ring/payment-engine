package com.optiva.topup.voms.rest.resources.userregistrationconfirmation;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRegistrationConfirmationDto {

  private String userRegistrationToken;
  private String firstName;
  private String lastName;
  private String password;

}
