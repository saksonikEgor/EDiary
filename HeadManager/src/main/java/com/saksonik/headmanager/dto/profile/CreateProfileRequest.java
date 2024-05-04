package com.saksonik.headmanager.dto.profile;

import java.util.UUID;

public record CreateProfileRequest(
        UUID userId,
        String name,
        String surname,
        String patronymic) {
}
