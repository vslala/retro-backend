package com.boards.core.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;

@EnableSwagger2
@Configuration
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .securityContexts(List.of(SecurityContext.builder().securityReferences(defaultAuth()).build()))
                .securitySchemes(List.of(apiKeys()))
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.boards.core.controllers"))
                .paths(PathSelectors.any())
                .build();
    }

    private List<SecurityReference> defaultAuth() {
        var authorizationScope = new AuthorizationScope("global", "Access main APIs");
        var authorizationScopes = new AuthorizationScope[]{authorizationScope};
        return List.of(new SecurityReference("JWT", authorizationScopes));
    }

    private ApiKey apiKeys() {
        return new ApiKey("JWT", "Authorization", "header");
    }
}
