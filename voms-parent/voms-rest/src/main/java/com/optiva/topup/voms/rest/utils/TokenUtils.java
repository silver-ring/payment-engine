package com.optiva.topup.voms.rest.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Component;

@Component
public class TokenUtils {

  private final TokenStore tokenStore;

  @Autowired
  public TokenUtils(TokenStore tokenStore) {
    this.tokenStore = tokenStore;
  }

  public void invalidateUserTokens(String username) {
    tokenStore.findTokensByClientId(username)
        .forEach(oauth2AccessToken -> tokenStore.removeAccessToken(oauth2AccessToken));
  }

  public void invalidateUserTokens() {
    String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    tokenStore.findTokensByClientId(email)
        .forEach(oauth2AccessToken -> tokenStore.removeAccessToken(oauth2AccessToken));
  }

}
