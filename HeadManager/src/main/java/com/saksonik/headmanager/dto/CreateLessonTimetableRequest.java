package com.saksonik.headmanager.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
public class CreateLessonTimetableRequest {
    private LocalDate date;
    private List<LessonDTO> lessons;

    @AllArgsConstructor
    @Getter
    public static class LessonDTO {
        private Integer classRoomId;
        private UUID teacherId;
        private Integer subjectId;
        private Integer scheduledCallId;
    }
}
