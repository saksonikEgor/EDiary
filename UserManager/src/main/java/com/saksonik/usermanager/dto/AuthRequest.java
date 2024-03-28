package com.saksonik.usermanager.dto;

public record AuthRequest(
        String password,
        String login,
        String email

) {
}
