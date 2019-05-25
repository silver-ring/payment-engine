package com.optiva.topup.voms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.ws.config.annotation.EnableWs;

@SpringBootApplication
@EnableWs
public class VomsSoapApp {

  public static void main(String[] args) {
    SpringApplication.run(VomsSoapApp.class, args);
  }

}
