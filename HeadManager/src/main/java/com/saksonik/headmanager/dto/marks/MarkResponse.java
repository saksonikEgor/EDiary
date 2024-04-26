package com.saksonik.headmanager.dto.marks;

import java.time.LocalDate;
import java.time.OffsetDateTime;

public record MarkResponse(
        Integer markId,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt,
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
