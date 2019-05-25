package com.optiva.topup.voms.rest.resources.logout;

import com.optiva.topup.voms.rest.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@PreAuthorize("isAuthenticated()")
public class Logout {

  @Autowired
  private TokenUtils tokenUtils;

  @PostMapping("/authentication/logout")
  public void authenticationLogout() {
    tokenUtils.invalidateUserTokens();
  }

}
