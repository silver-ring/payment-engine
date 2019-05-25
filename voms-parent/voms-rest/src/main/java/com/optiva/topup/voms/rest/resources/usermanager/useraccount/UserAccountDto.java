package com.optiva.topup.voms.rest.resources.usermanager.useraccount;

import com.optiva.topup.voms.common.types.UserAccountStatus;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
public class UserAccountDto extends RepresentationModel<UserAccountDto> {

  private String email;
  private String firstName;
  private String lastName;
  private UserAccountStatus userAccountStatus;

}
