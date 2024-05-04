package com.saksonik.headmanager.dto.callSchedule;

import java.time.LocalTime;
import java.util.UUID;

public record ScheduledCallDTO(
        UUID callNumber,
        LocalTime start,
        LocalTime end
) {
}
