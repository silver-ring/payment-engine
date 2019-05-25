package com.optiva.topup.voms.rest.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;

@Configuration
public class RestRepositoriesConfig implements RepositoryRestConfigurer {

  public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
    config.setExposeRepositoryMethodsByDefault(false);
  }

}
