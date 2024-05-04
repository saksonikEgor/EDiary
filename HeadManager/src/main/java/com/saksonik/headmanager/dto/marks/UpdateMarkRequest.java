package com.saksonik.headmanager.dto.marks;

import java.util.UUID;

public record UpdateMarkRequest(
        String description,
        UUID markTypeId,
        UUID workTypeId
) {
}
