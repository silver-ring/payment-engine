package com.optiva.topup.voms.security.config;

import com.optiva.topup.voms.common.repositories.configparameters.UserManagementConfigParameterRepo;
import javax.sql.DataSource;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

@EnableAuthorizationServer
@Configuration
@Log4j2
public class AuthorizationServerConfigurer extends AuthorizationServerConfigurerAdapter {

  private final DefaultClientDetailsService defaultClientDetailsService;
  private final DefaultTokenEnhancer defaultTokenEnhancer;
  private final DataSource dataSource;
  private final UserAuthorizationFilter userAuthorizationFilter;
  private final UserManagementConfigParameterRepo configParameterRepo;

  @Autowired
  public AuthorizationServerConfigurer(DefaultClientDetailsService defaultClientDetailsService,
      DefaultTokenEnhancer defaultTokenEnhancer, DataSource dataSource,
      UserAuthorizationFilter userAuthorizationFilter,
      UserManagementConfigParameterRepo configParameterRepo) {
    this.defaultClientDetailsService = defaultClientDetailsService;
    this.defaultTokenEnhancer = defaultTokenEnhancer;
    this.dataSource = dataSource;
    this.userAuthorizationFilter = userAuthorizationFilter;
    this.configParameterRepo = configParameterRepo;
  }

  @Override
  public void configure(AuthorizationServerSecurityConfigurer oauthServer) {
    oauthServer.tokenKeyAccess("isAnonymous()")
        .checkTokenAccess("hasAuthority('ROLE_TRUSTED_CLIENT')")
        .addTokenEndpointAuthenticationFilter(userAuthorizationFilter);
  }

  @Override
  public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
    clients.withClientDetails(defaultClientDetailsService);
  }

  @Override
  public void configure(final AuthorizationServerEndpointsConfigurer endpoints) {
    endpoints.tokenServices(tokenServices());
    endpoints.tokenStore(tokenStore());
    endpoints.tokenEnhancer(defaultTokenEnhancer);
  }

  @Bean
  public DefaultTokenServices tokenServices() {
    AuthorizationServerTokenServices tokenServices = new AuthorizationServerTokenServices();
    tokenServices.setTokenStore(tokenStore());
    tokenServices.setTokenEnhancer(defaultTokenEnhancer);
    tokenServices.setUserManagementConfigParameterRepo(configParameterRepo);
    return tokenServices;
  }

  @Bean
  public TokenStore tokenStore() {
    return new JdbcTokenStore(dataSource);
  }

}
