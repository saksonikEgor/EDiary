package com.saksonik.headmanager.dto.meetings;

import java.time.OffsetDateTime;
import java.util.UUID;

public record UpdateMeetingRequest(
        OffsetDateTime dateTime,
        String description,
        UUID classroomId
) {
}
