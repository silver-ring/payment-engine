package com.optiva.topup.voms.soap.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.csrf().disable().authorizeRequests().anyRequest().permitAll();
  }

  @Override
  public void configure(WebSecurity web) {
    String[] antPatterns = {
            getActuatorHealthUrl()
    };

    web.ignoring().antMatchers(
            antPatterns
    );

  }

  private String getActuatorHealthUrl() {
    return "/actuator/health";
  }

}
