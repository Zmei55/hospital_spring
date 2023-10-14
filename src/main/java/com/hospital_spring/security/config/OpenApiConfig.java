package com.hospital_spring.security.config;

import io.swagger.v3.oas.models.*;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.parameters.RequestBody;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.hospital_spring.security.filter.JwtAuthenticationFilter.AUTHENTICATION_URL;
import static com.hospital_spring.security.filter.JwtAuthenticationFilter.USERNAME_PARAMETER;

// оформление OpenApi для аутентификации
@Configuration
public class OpenApiConfig {
    public static final String BEARER_AUTH = "bearerAuth";

    @Bean
    public OpenAPI customOpenApi() {
        return new OpenAPI()
            .addSecurityItem(buildSecurity())
            .paths(buildAuthenticationPath())
            .components(buildComponents())
            .info(buildInfo());
    }

    // оформление пути
    private Paths buildAuthenticationPath() {
        return new Paths()
            .addPathItem(AUTHENTICATION_URL, buildAuthenticationPathItem());
    }

    // оформление запроса
    private PathItem buildAuthenticationPathItem() {
        return new PathItem().post(
            new Operation()
                .addTagsItem("Authentication")
                .requestBody(buildAuthenticationRequestBody())
                .responses(buildAuthenticationResponses())
        );
    }

    // оформление раздела запросов
    private RequestBody buildAuthenticationRequestBody() {
        return new RequestBody().content(
            new Content().addMediaType(
                "application/x-www-form-urlencoded",
                new MediaType().schema(
                    new Schema<>().$ref("EmailAndPassword")
                )
            )
        );
    }

    // оформление раздела ответов
    private ApiResponses buildAuthenticationResponses() {
        return new ApiResponses().addApiResponse(
            "200", new ApiResponse().content(
                new Content().addMediaType(
                    "application/json", new MediaType().schema(
                        new Schema<>().$ref("Tokens")
                    )
                )
            )
        );
    }

    // оформление раздела безопасности
    private SecurityRequirement buildSecurity() {
        return new SecurityRequirement()
            .addList(BEARER_AUTH);
    }

    // оформление раздела информации
    private Info buildInfo() {
        return new Info()
            .title("Hospital API")
            .version("0.1");
    }

    // оформление раздела "компоненты" (содержит все компоненты схемы (дто)) "Components"
    private Components buildComponents() {
        Schema<?> emailAndPassword = new Schema<>()
            .type("object")
            .description("Email and password of the user")
            .addProperty(USERNAME_PARAMETER, new Schema<>().type("string"))
            .addProperty("password", new Schema<>().type("string"));

        Schema<?> tokens = new Schema<>()
            .type("object")
            .description("Access and refresh tokens")
            .addProperty("accessToken", new Schema<>().type("string"))
            .addProperty("refreshToken", new Schema<>().type("string"));

        SecurityScheme securityScheme = new SecurityScheme()
            .name(BEARER_AUTH)
            .type(SecurityScheme.Type.HTTP)
            .scheme("bearer")
            .bearerFormat("JWT");

        return new Components()
            .addSchemas("EmailAndPassword", emailAndPassword)
            .addSchemas("Tokens", tokens)
            .addSecuritySchemes(BEARER_AUTH, securityScheme);
    }
}
