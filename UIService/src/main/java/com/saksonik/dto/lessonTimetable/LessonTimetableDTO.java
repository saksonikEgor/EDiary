package com.saksonik.dto.lessonTimetable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class LessonTimetableDTO {
    private LocalDate date;
    private List<LessonDTO> lessons;

    @AllArgsConstructor
    @Getter
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
