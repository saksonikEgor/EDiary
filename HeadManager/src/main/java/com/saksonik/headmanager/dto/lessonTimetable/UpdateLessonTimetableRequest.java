package com.saksonik.headmanager.dto.lessonTimetable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
public class UpdateLessonTimetableRequest {
    private LocalDate date;
    private List<LessonDTO> lessons;

    @AllArgsConstructor
    @Getter
    public static class LessonDTO {
        private Integer lessonId;
        private Integer classRoomId;
        private UUID teacherId;
        private Integer subjectId;
        private Integer scheduledCallId;
    }
}
