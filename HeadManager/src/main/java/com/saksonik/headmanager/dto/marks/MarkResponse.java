package com.saksonik.headmanager.dto.marks;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.UUID;

public record MarkResponse(
        UUID markId,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        LocalDate date,
        String description,
        String studentName,
        String teacherName,
        String subjectName,
        String workTypeName,
        String markTypeName,
        String studyPeriod
) {
}
