package com.saksonik.headmanager.dto.meetings;

import java.time.OffsetDateTime;

public record CreateMeetingRequest(
        OffsetDateTime dateTime,
        String description,
        Integer classId,
        Integer classroomId
) {
}
