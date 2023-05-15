package com.pim.projects.besttravel.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Best Travel API",
                version = "1.0",
                description = "Documentation for endpoints in Best Travel APP"
        )
)
public class OpenApiConfig {

    //URL to access Swagger UI
    //http://localhost:8080/best_travel/swagger-ui/index.html
}
