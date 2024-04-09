package com.saksonik.headmanager.dto.lessonTimetable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
public class LessonTimetableDTO {
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
            private Integer lessonNumber;
            private String classRoom;
            private String teacherFullName;
            private String subject;
            private LocalTime startTime;
            private LocalTime endTime;
            private LocalDate date;
        }
    }
}
