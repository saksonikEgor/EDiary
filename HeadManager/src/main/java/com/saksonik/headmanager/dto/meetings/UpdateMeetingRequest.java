package com.saksonik.headmanager.dto.meetings;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.UUID;

public record UpdateMeetingRequest(
        LocalDateTime dateTime,
        String description,
        UUID classroomId
) {
}
