package com.saksonik.headmanager.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
public class LessonTimetableRequest {
//    private List<DayDTO> days;
//
//    @Data
//    public static class DayDTO {
        private LocalDate date;
        private List<LessonDTO> lessons = new ArrayList<>();

        public void addLesson(LessonDTO lesson) {
            lessons.add(lesson);
        }

        @AllArgsConstructor
        @Getter
        public static class LessonDTO {
            private Integer classRoomId;
            private UUID teacherId;
            private Integer subjectId;
            private Integer scheduledCallId;
        }
//    }
}
