package com.saksonik.headmanager.dto.profile;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class UserDTO {
    private String role;
    private UUID userId;
    private String fullName;
//    private String email;
//    private String phone;
    private List<ChildDTO> children;
    private List<String> parents;
    private String classForStudent;
    private List<String> classesForTeacher;
    private List<String> classesForClassroomTeacher;
    private List<String> subjects;

    @AllArgsConstructor
    public static class ChildDTO {
        private String fullName;
        private String className;
    }
}
