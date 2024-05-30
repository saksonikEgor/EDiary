package com.saksonik.headmanager.dto.user;

public record UserRegistration(
        String username,
        String role,
        String email,
        String firstName,
        String lastName,
        String patronymic) {
}
