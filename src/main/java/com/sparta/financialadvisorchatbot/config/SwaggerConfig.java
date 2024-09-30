package com.sparta.financialadvisorchatbot.config;

import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.Components;


@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Sparta Global Financial Chatbot Business Analyst Service API")
                        .description("Documentation of the API used for exploring the Sparta Global Financial Chatbot conversations.")
                        .version("1.0.0"));
    }
}
