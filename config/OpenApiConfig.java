package com.example.backend.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
    info = @Info(
        title = "틈짓 API",
        version = "1.0.0",
        description = """
            틈짓의 API 문서입니다.
            """
    )
)
public class OpenApiConfig {
}