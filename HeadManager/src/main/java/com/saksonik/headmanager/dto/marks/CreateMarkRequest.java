package com.saksonik.headmanager.dto.marks;

import java.time.LocalDate;
import java.util.UUID;

public record CreateMarkRequest(
        UUID studentId,
        UUID subjectId,
        UUID workTypeId,
        UUID markTypeId,
        String description,
        LocalDate date
) {
}
