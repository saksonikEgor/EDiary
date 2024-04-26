package com.saksonik.headmanager.dto.meetings;

import java.time.OffsetDateTime;

public record MeetingResponse(
        Integer meetingId,
        OffsetDateTime dateTime,
        String description,
        String className,
        String classroomName
) {
}
