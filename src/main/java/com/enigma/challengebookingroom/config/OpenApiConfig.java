package com.enigma.challengebookingroom.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition
        (
                info = @Info(
                        description = "Documentation Booking Room API",
                        title = "Documentation Booking Room API",
                        version = "v.1.0",
                        termsOfService = "Terms of Service"
                ),
                servers = {
                        @Server(
                                description = "Local ENV",
                                url = "http://localhost:8081"
                        ),
                        @Server(
                                description = "Remote ENV",
                                url = "remote-server"
                        )
                }
        )
@SecurityScheme(
        name = "bearerAuth",
        description = "JWT Auth description",
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)
public class OpenApiConfig {
}
