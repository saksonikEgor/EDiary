package com.saksonik.usermanager.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.Date;

@Component
public class JWTHandler {
    @Value("${jwt-secret-key}")
    private String secretKey;
    @Value("${spring.application.name}")
    private String applicationName;

    public String generateToken(String username) {
        Date expirationDate = Date.from(ZonedDateTime.now().plusMinutes(60).toInstant());

        return JWT.create()
                .withSubject("User details")
                .withClaim("username", username)
                .withIssuedAt(ZonedDateTime.now().toInstant())
                .withIssuer(applicationName)
                .withExpiresAt(expirationDate)
                .sign(Algorithm.HMAC256(secretKey));
    }
}
