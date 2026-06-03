package com.gac.api.infrastructure.configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    private static final String BEARER_SCHEME = "Bearer Authentication";

    @Bean
    public OpenAPI gacOpenApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("GAC API")
                        .description(
                                "REST API for patrimonial management of projectors and keys at CCT/UNIFOR. "
                                        + "Requirements v1.3. Authenticate via POST /api/auth/login and use the JWT in Authorize.")
                        .version("1.3.0")
                        .contact(new Contact().name("GAC Backend").email("admin@gac.local")))
                .addSecurityItem(new SecurityRequirement().addList(BEARER_SCHEME))
                .components(new Components()
                        .addSecuritySchemes(
                                BEARER_SCHEME,
                                new SecurityScheme()
                                        .name(BEARER_SCHEME)
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")
                                        .description("JWT obtained from POST /api/auth/login")));
    }
}
