package com.optiva.topup.voms.rest.resources.usermanager.updateuserroles;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateUserRolesDto {

  private String email;
  private List<String> userRoles;

}
