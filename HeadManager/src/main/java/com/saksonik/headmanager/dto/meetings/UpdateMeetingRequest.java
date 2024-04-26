package com.saksonik.headmanager.dto.meetings;

import java.time.OffsetDateTime;

public record UpdateMeetingRequest(
        OffsetDateTime dateTime,
        String description,
        Integer classroomId
) {
}
