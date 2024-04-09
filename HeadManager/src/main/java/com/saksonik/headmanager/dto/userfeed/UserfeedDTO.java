package com.saksonik.headmanager.dto.userfeed;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class UserfeedDTO {
    private UUID id;
    private String role;
    private List<Class> classes;
    private List<Child> children;

    @AllArgsConstructor
    public static class Class {
        private Integer classId;
        private String name;
    }

    @AllArgsConstructor
    public static class Child {
        private UUID childId;
        private String fullName;
        private String className;
    }
}
