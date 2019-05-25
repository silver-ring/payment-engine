package com.optiva.topup.voms;

import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class VomsDbInitApp {

  public static void main(String[] args) {
    ConfigurableApplicationContext ctx = SpringApplication.run(VomsDbInitApp.class, args);
    SpringApplication.exit(ctx, (ExitCodeGenerator) () -> 0);
  }

}
