package com.saksonik.headmanager.dto.meetings;

import java.time.OffsetDateTime;
import java.util.UUID;

public record CreateMeetingRequest(
        OffsetDateTime dateTime,
        String description,
        UUID classId,
        UUID classroomId
) {
}
