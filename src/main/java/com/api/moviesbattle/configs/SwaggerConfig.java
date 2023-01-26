package com.api.moviesbattle.configs;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI springShopOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("Movies Battle API")
                        .description("Movies Battle Application")
                        .version("v0.0.1")
                        .license(new License().name("Apache 2.0").url("http://springdoc.org")))
                .externalDocs(new ExternalDocumentation()
                        .description("Movies Battle Documentation")
                        .url("https://github.com/wallaxmello/movies-battle.git"))
                .components(new Components()
                        .addSecuritySchemes("Bearer",
                                new SecurityScheme().name("Authorization").type(SecurityScheme.Type.HTTP).scheme("Bearer").bearerFormat("JWT")));
    }
}
