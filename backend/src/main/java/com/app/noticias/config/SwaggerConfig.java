package com.app.noticias.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(info = @Info(title = "API Noticias", version = "v1"))
@SecurityScheme(
        name = "bearerAuth", // Nombre del esquema de seguridad
        type = SecuritySchemeType.HTTP, // Tipo HTTP
        scheme = "bearer", // Usando el esquema de autenticación Bearer
        bearerFormat = "JWT", // Formato del token
        in = SecuritySchemeIn.HEADER, // El token se enviará en el encabezado Authorization
        description = "JWT Authorization header using the Bearer scheme" // Descripción
)
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI();
    }
}

