package com.erajayaapi.config.swagger2;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

   @Bean
   public Docket swaggerConfiguration() {
      return new Docket(DocumentationType.SWAGGER_2).select()
              .paths(PathSelectors.ant("/api/v1/order/*"))
              .apis(RequestHandlerSelectors.basePackage("com.erajayaapi"))
              .build()
              .apiInfo(apiInfo());
   }

   private ApiInfo apiInfo() {
      ApiInfo apiInfo = new ApiInfo(
              "Erajaya API Doc",
              "Some custom description of API.",
              "API V1",
              "Terms of service",
              new Contact("Eko Sutrisno",
                      "https://github.com/ekosutrisno",
                      "ekosutrisno801@gmail.com"),
              "License of API",
              "API license URL",
              Collections.emptyList());

      return apiInfo;
   }

}