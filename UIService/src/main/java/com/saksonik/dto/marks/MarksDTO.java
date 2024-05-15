package com.saksonik.dto.marks;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
public class MarksDTO {
    private String studyPeriodName;
    private UUID studyPeriodId;
    private LocalDate startPeriod;
    private LocalDate endPeriod;
    private List<SubjectDTO> subjects;

    @Data
    public static class SubjectDTO {
        private UUID id;
        private String subjectName;
        private List<MarkDTO> marks;
        private Float avg;

        @Data
        @AllArgsConstructor
        public static class MarkDTO {
            private UUID markId;
            private UUID studentId;
            private UUID teacherId;
            private String workType;
            private String markType;
            private LocalDateTime createdAt;
            private LocalDateTime updatedAt;
            private LocalDate date;
            private String description;
        }
    }
}
