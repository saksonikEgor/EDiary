package com.saksonik.headmanager.dto.meetings;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.UUID;

public record MeetingResponse(
        UUID meetingId,
        LocalDateTime dateTime,
        String description,
        String className,
        String classroomName
) {
}
