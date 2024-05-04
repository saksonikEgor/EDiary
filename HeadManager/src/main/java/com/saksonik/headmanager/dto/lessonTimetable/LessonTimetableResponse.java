package com.saksonik.headmanager.dto.lessonTimetable;

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
public class LessonTimetableResponse {
    private List<DayDTO> days;

    @Data
    public static class DayDTO {
        private LocalDate date;
        private List<LessonDTO> lessons = new ArrayList<>();

        public void addLesson(LessonDTO lesson) {
            lessons.add(lesson);
        }

        @AllArgsConstructor
        @Getter
        public static class LessonDTO {
            private UUID lessonNumber;
            private String classRoom;
            private String teacherFullName;
            private String subject;
            private LocalTime startTime;
            private LocalTime endTime;
            private LocalDate date;
        }
    }
}
