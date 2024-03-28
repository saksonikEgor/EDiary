package com.saksonik.gateway.filter;

import com.saksonik.gateway.service.JWTUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@RefreshScope
@Component
@RequiredArgsConstructor
@Log4j2
public class AuthenticationFilter implements GatewayFilter {
    private final RouterValidator validator;
    private final JWTUtils jwtUtils;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();

        if (validator.isSecured.test(request)) {
            if (authMissing(request)) {
                log.info("Gateway || Filter result: " + exchange);

                exchange.transformUrl("/auth/login");
                return chain.filter(exchange);

//                exchange.
//                return onError(exchange, HttpStatus.UNAUTHORIZED);
            }

            String token = request.getHeaders().getOrEmpty("Authorization").get(0);

            if (jwtUtils.isExpired(token)) {
                log.info("Gateway || Filter result: " + exchange);
//                return onError(exchange, HttpStatus.UNAUTHORIZED);

                exchange.transformUrl("/auth/login");
                return chain.filter(exchange);
            }
        }

        log.info("Gateway || Filter result: " + exchange);
        return chain.filter(exchange);
    }

    private Mono<Void> onError(ServerWebExchange exchange, HttpStatus httpStatus) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);
        return response.setComplete();
    }

    private boolean authMissing(ServerHttpRequest request) {
        return !request.getHeaders().containsKey("Authorization");
    }
}
