package com.erajayaapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ErajayaApiApplication {

   public static void main(String[] args) {
      System.setProperty("spring.config.name", "application");
      SpringApplication.run(ErajayaApiApplication.class, args);
   }

}
