package com.saksonik.authenticator.dto;

public record AuthRequest(
        String password,
        String login,
        String email

) {
}
