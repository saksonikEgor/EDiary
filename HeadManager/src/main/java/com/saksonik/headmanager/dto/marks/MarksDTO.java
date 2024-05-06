package com.saksonik.headmanager.dto.marks;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;

@Data
public class MarksDTO {
    private String role;
    private List<SubjectDTO> subjects;

    @Data
    public static class SubjectDTO {
        private String subjectName;
        private List<MarkDTO> marks;
        private Float avg;

        @Data
        @AllArgsConstructor
        public static class MarkDTO {
            private String workType;
            private String markType;
            private LocalDateTime createdAt;
            private LocalDateTime updatedAt;
            private String description;
        }
    }
}
