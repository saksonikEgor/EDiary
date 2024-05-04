package com.saksonik.headmanager.dto.meetings;

import java.time.OffsetDateTime;
import java.util.UUID;

public record MeetingResponse(
        UUID meetingId,
        OffsetDateTime dateTime,
        String description,
        String className,
        String classroomName
) {
}
