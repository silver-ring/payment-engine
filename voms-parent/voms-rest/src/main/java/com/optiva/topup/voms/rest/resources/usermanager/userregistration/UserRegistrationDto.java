package com.optiva.topup.voms.rest.resources.usermanager.userregistration;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRegistrationDto {

  List<String> userRoles;
  String email;

}
