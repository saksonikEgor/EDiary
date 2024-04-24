package com.saksonik.headmanager.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class UserfeedDTO {
    private UUID id;
    private String role;
    private List<ClassDTO> classes;
    private List<ChildDTO> children;

    @AllArgsConstructor
    public static class ClassDTO {
        private Integer classId;
        private String name;
    }

    @AllArgsConstructor
    public static class ChildDTO {
        private UUID childId;
        private String fullName;
        private String className;
    }
}
