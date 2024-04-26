package com.saksonik.headmanager.dto.marks;

public record UpdateMarkRequest(
        String description,
        Integer markTypeId,
        Integer workTypeId
) {
}
