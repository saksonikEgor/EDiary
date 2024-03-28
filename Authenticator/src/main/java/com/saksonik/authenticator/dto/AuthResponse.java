package com.saksonik.authenticator.dto;

public record AuthResponse(
        String accessToken,
        String refreshToken
) {
}
