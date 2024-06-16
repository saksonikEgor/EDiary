package com.saksonik.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.client.oidc.web.server.logout.OidcClientInitiatedServerLogoutSuccessHandler;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.logout.ServerLogoutSuccessHandler;

@Slf4j
@Configuration
public class SecurityConfig {
    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http, ServerLogoutSuccessHandler handler) {
        return http
                .authorizeExchange(customizer -> customizer
                        .pathMatchers("/img/**", "/js/**", "/css/**")
                        .permitAll()
                        .pathMatchers(HttpMethod.GET, "/")
                        .permitAll()
                        .anyExchange().authenticated())
                .oauth2Login(Customizer.withDefaults())
                .logout(logoutSpec -> logoutSpec.logoutSuccessHandler(handler))
                .oauth2Client(Customizer.withDefaults())
                .build();
    }

    @Bean
    public ServerLogoutSuccessHandler keycloakLogoutHandler(ReactiveClientRegistrationRepository repository) {
        OidcClientInitiatedServerLogoutSuccessHandler oidcLogoutSuccessHandler =
                new OidcClientInitiatedServerLogoutSuccessHandler(repository);

        oidcLogoutSuccessHandler.setPostLogoutRedirectUri("{baseUrl}/");

        return oidcLogoutSuccessHandler;
    }
}
