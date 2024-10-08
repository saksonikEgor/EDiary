package com.saksonik.headmanager.dto.meetings;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
public class MeetingScheduleDTO {
    private String className;
    private UUID classId;
    private List<MeetingDTO> meetings;

    @Data
    @AllArgsConstructor
    public static class MeetingDTO {
        private UUID meetingId;
        private UUID classroomId;
        private String classroom;
        private LocalDateTime date;
        private String description;
    }
}
