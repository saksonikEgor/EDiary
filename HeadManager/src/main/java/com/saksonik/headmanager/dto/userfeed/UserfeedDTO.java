package com.saksonik.headmanager.dto.userfeed;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.saksonik.headmanager.util.UserfeedSerializer;
import lombok.Data;
import lombok.ToString;

import java.util.List;
import java.util.UUID;

@Data
@ToString
@JsonSerialize(using = UserfeedSerializer.class)
public class UserfeedDTO {
    private UUID userId;
    private List<String> roles;
    private List<ClassToSubjectsDTO> teachingClasses;
    private ClassDTO studyingClass;
    private List<ChildDTO> children;
    private List<ClassDTO> managedClasses;

    public record ClassToSubjectsDTO(ClassDTO classDTO, List<SubjectDTO> subjects) {
    }

    public record SubjectDTO(UUID subjectId, String name) {
    }

    public record ClassDTO(UUID classId, String name) {
    }

    public record ChildDTO(UUID childId, String fullName, ClassDTO learningClass) {
    }
}
