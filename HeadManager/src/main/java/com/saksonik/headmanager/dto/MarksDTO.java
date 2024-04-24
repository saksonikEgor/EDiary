package com.saksonik.headmanager.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

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
            private OffsetDateTime createdAt;
            private OffsetDateTime updatedAt;
            private String description;
        }
    }
}
