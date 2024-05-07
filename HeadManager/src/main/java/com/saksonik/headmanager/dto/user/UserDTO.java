package com.saksonik.headmanager.dto.user;

import java.util.UUID;

public record UserDTO(
        UUID id,
        String fullName
) {
}
