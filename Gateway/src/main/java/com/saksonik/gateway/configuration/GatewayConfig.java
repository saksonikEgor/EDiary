package com.saksonik.gateway.configuration;

import com.saksonik.gateway.filter.AuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class GatewayConfig {
    private final AuthenticationFilter filter;

    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("head-manager", r -> r.path("/head-manager/**")
                        .filters(f -> f.filter(filter))
                        .uri("lb://HEAD-MANAGER"))
                .route("notificator", r -> r.path("/notificator/**")
                        .filters(f -> f.filter(filter))
                        .uri("lb://NOTIFICATOR"))
                .route("authenticator", r -> r.path("/auth/**")
                        .filters(f -> f.filter(filter))
                        .uri("lb://AUTHENTICATOR"))
                .build();
    }
}
