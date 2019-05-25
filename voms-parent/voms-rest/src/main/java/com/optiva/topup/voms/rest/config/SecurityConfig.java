package com.optiva.topup.voms.rest.config;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  private final DataSource dataSource;

  @Autowired
  public SecurityConfig(DataSource dataSource) {
    this.dataSource = dataSource;
  }

  @Bean
  public TokenStore tokenStore() {
    return new JdbcTokenStore(dataSource);
  }

  @Override
  public void configure(WebSecurity web) {

    String[] antPatterns = {
        getUserRegistrationConfirmationCheckTokenUrl(),
        getUserRegistrationConfirmationUrl(),
        getResetUserPasswordUrl(),
        getResetUserPasswordCheckTokenUrl(),
        getChangePasswordConfirmationCheckTokenUrl(),
        getChangePasswordConfirmationUrl(),
        getActuatorHealthUrl()
    };

    web.ignoring().antMatchers(
        antPatterns
    );

  }

  private String getUserRegistrationConfirmationCheckTokenUrl() {
    return "/userRegistrationConfirmation/checkToken";
  }

  private String getUserRegistrationConfirmationUrl() {
    return "/userRegistrationConfirmation";
  }

  private String getResetUserPasswordCheckTokenUrl() {
    return "/resetUserPasswordConfirmation/checkToken";
  }

  private String getResetUserPasswordUrl() {
    return "/resetUserPasswordConfirmation";
  }

  private String getChangePasswordConfirmationCheckTokenUrl() {
    return "/changePasswordConfirmation/checkToken";
  }

  private String getChangePasswordConfirmationUrl() {
    return "/changePasswordConfirmation";
  }

  private String getActuatorHealthUrl() {
    return "/actuator/health";
  }

}
