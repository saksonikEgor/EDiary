package com.saksonik.headmanager.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.OffsetDateTime;
import java.util.List;

@Data
public class MeetingScheduleDTO {
    private String role;
    private List<MeetingDTO> meetings;

    @Data
    @AllArgsConstructor
    public static class MeetingDTO {
        private String className;
        private String classRoom;
        private OffsetDateTime date;
        private String description;
    }
}
