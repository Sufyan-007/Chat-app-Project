package com.ChatApp.Config;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Value("${oauth2.authorization-url}")
    private String authorizationUrl;

    @Value("${oauth2.token-url}")
    private String tokenUrl;

    @Bean
    public OpenAPI customOpenAPI() {
        final String securitySchemeName = "bearerAuth";
        final String oauthSchemeName = "oauth2";


        return new OpenAPI()
                .info(new Info().title("ChatApp API").version("1.0"))
                .addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
                .components(
                        new Components()
                                .addSecuritySchemes(securitySchemeName,
                                        new SecurityScheme()
                                                .name(securitySchemeName)
                                                .type(SecurityScheme.Type.HTTP)
                                                .scheme("bearer")
                                                .bearerFormat("JWT") // Optional: for UI display
                                ).addSecuritySchemes(
                                        oauthSchemeName,
                                        new SecurityScheme()
                                                .name(oauthSchemeName)
                                                .type(SecurityScheme.Type.OAUTH2)
                                                .flows(
                                                        new OAuthFlows()
                                                                .authorizationCode(
                                                                        new OAuthFlow()
                                                                                .authorizationUrl(authorizationUrl)
                                                                                .tokenUrl(tokenUrl)
                                                                                .scopes(
                                                                                        new Scopes()
                                                                                                .addString("openid", "Access OpenID Connect ID token")
                                                                                                .addString("email", "Access user's email address")
                                                                                                .addString("profile", "Access user's basic profile info")
                                                                                )
                                                                )
                                                )
                                        )
                );
    }
}
