package com.saksonik.headmanager.dto.marks;

import java.time.LocalDate;
import java.util.UUID;

public record CreateMarkRequest(
        UUID studentId,
        LocalDate date,
        Integer subjectId,
        Integer workTypeId,
        Integer markTypeId,
        String description
) {
}
