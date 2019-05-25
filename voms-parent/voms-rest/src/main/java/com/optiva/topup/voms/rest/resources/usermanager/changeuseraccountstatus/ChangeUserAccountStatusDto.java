package com.optiva.topup.voms.rest.resources.usermanager.changeuseraccountstatus;

import com.optiva.topup.voms.common.types.UserAccountStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangeUserAccountStatusDto {

  private String email;
  private UserAccountStatus newUserAccountStatus;

}
