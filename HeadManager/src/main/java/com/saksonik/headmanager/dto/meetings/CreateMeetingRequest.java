package com.saksonik.headmanager.dto.meetings;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.UUID;

public record CreateMeetingRequest(
        LocalDateTime dateTime,
        String description,
        UUID classId,
        UUID classroomId
) {
}
