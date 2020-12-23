package com.erajayaapi.config.swagger2;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    private static final Set<String> DEFAUL_REQUEST_RESPONSE = new HashSet<String>(
            Arrays.asList(
                    MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_JSON_VALUE
            )
    );

    @Bean
    public Docket swaggerConfiguration() {
        return new Docket(DocumentationType.SWAGGER_2).select()
                .paths(PathSelectors.ant("/api/v1/order/*"))
                .apis(RequestHandlerSelectors.basePackage("com.erajayaapi"))
                .build().groupName("Order Service")
                .apiInfo(apiInfo())
                .useDefaultResponseMessages(false)
                .produces(DEFAUL_REQUEST_RESPONSE)
                .consumes(DEFAUL_REQUEST_RESPONSE);
    }

    private ApiInfo apiInfo() {

        return new ApiInfo(
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
    }

}