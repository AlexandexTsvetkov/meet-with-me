package ru.aston.meet.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Конфигурация для Swagger
 * Доступен по :
 * http://localhost:8081/swagger-ui.html
 * или
 * http://localhost:8081/swagger-ui/index.html
 */
@Configuration
@SecurityScheme(
        name = "Bearer Authentication",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT"
)
@OpenAPIDefinition(
        info = @Info(
                title = "REST API documentation",
                version = "1.0",
                description = """
                        Meeting scheduling and management app
                        """
        ),
        security = @SecurityRequirement(name = "Bearer Authentication")
)
public class OpenApiConfig {

    @Bean
    public GroupedOpenApi api() {
        return GroupedOpenApi.builder()
                .group("rest-api")
                .pathsToMatch("/**")
                .build();
    }
}
