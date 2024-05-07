package com.saksonik.dto.callSchedule;

import java.time.LocalTime;

public record ScheduledCallDTO(
        Integer callNumber,
        LocalTime start,
        LocalTime end
) {
}
