package com.optiva.topup.voms.security.config;

import com.optiva.topup.voms.common.entities.configparameters.UserManagementConfigParameter;
import com.optiva.topup.voms.common.repositories.configparameters.UserManagementConfigParameterRepo;
import com.optiva.topup.voms.common.types.UserManagementConfigParameterType;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

@Log4j2
public class AuthorizationServerTokenServices extends DefaultTokenServices {

  private TokenStore tokenStore;

  private UserManagementConfigParameterRepo configParameterRepo;

  public void setUserManagementConfigParameterRepo(
      UserManagementConfigParameterRepo configParameterRepo) {
    this.configParameterRepo = configParameterRepo;
  }

  public void afterPropertiesSet() {
    Assert.notNull(tokenStore, "tokenStore must be set");
    Assert.notNull(configParameterRepo, "configParameterRepo must be set");
  }

  @Transactional
  public OAuth2AccessToken createAccessToken(
      OAuth2Authentication authentication) throws AuthenticationException {
    OAuth2AccessToken existingAccessToken = tokenStore.getAccessToken(authentication);
    if (existingAccessToken != null) {
      tokenStore.removeAccessToken(existingAccessToken);
    }

    UserManagementConfigParameter userManagementConfigParameter =
        configParameterRepo.findByParameter(
            UserManagementConfigParameterType.LOGIN_TOKEN_EXPIRATION_DURATION);

    Integer tokenExpirationHours = Integer.parseInt(userManagementConfigParameter.getValue());

    Integer tokenExpirationSeconds = tokenExpirationHours * 60 * 60;

    setAccessTokenValiditySeconds(tokenExpirationSeconds);
    return super.createAccessToken(authentication);
  }

  @Override
  public void setTokenStore(TokenStore tokenStore) {
    this.tokenStore = tokenStore;
    super.setTokenStore(tokenStore);
  }

}
