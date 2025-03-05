package com.backend.conferencias.config;

import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

@Configuration
@OpenAPIDefinition(info = @Info(title = "API RESTful para el Sistema de Gestión de Reservas Académicas", version = "1.0", description = "Esta API proporciona un conjunto de endpoints para gestionar el sistema de reservas académicas. Permite realizar operaciones CRUD sobre conferencistas, auditorios y reservas, así como la autenticación y autorización de usuarios mediante JWT. La API está diseñada para ser utilizada por aplicaciones frontend que requieren interactuar con el sistema de reservas de manera segura y eficiente. Incluye documentación detallada y ejemplos de uso para facilitar la integración.", license = @License(name = "MIT License", url = "https://opensource.org/licenses/MIT"), contact = @Contact(name = "John Mata & Isabel Pazto", email = "john.mata@epn.edu.ec")))
@SecurityScheme(name = "Bearer Authentication", type = SecuritySchemeType.HTTP, bearerFormat = "JWT", scheme = "bearer")
public class OpenApiConfig {
}
