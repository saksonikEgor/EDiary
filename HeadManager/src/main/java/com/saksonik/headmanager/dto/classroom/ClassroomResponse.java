package com.saksonik.headmanager.dto.classroom;

import java.util.UUID;

public record ClassroomResponse(
        UUID id,
        String name) {
}
