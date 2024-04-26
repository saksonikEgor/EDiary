package com.saksonik.headmanager.dto.profile;

public record CreateProfileRequest(
        String name,
        String surname,
        String patronymic) {
}
