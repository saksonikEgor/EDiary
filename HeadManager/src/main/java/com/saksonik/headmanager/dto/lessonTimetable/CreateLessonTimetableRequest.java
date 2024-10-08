package com.saksonik.headmanager.dto.lessonTimetable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDate;
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
        private UUID classRoomId;
        private UUID teacherId;
        private UUID subjectId;
        private Integer scheduledCallId;
    }
}
