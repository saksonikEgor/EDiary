package com.saksonik.headmanager.dto.lessonTimetable;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LessonTimetableResponse {
    private LocalDate date;
    private List<LessonDTO> lessons = new ArrayList<>();

    public void addLesson(LessonDTO lesson) {
        lessons.add(lesson);
    }

    @AllArgsConstructor
    @Getter
    @ToString
    public static class LessonDTO {
        private Integer lessonNumber;
        private String classRoom;
        private String className;
        private String teacherFullName;
        private String subject;
        private LocalTime startTime;
        private LocalTime endTime;
        private LocalDate date;
    }
}
