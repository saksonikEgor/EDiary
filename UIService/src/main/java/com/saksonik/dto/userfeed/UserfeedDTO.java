package com.saksonik.dto.userfeed;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.saksonik.util.userfeedDeserializer.ClassDTODeserializer;
import com.saksonik.util.userfeedDeserializer.SubjectDTODeserializer;
import com.saksonik.util.userfeedDeserializer.UserfeedDeserializer;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@JsonDeserialize(using = UserfeedDeserializer.class)
@ToString
public class UserfeedDTO {
    private UUID userId;
    private List<String> roles;
    private List<ClassToSubjectsDTO> teachingClasses;
    private ClassDTO studyingClass;
    private List<ChildDTO> children;
    private List<ClassDTO> managedClasses;

    @NoArgsConstructor
    @Setter
    @Getter
    @ToString
    public static class ClassToSubjectsDTO {
        private ClassDTO classDTO;
        private List<SubjectDTO> subjects;

    }

    @JsonDeserialize(using = SubjectDTODeserializer.class)
    public record SubjectDTO(UUID subjectId, String name) {
    }

    @JsonDeserialize(using = ClassDTODeserializer.class)
    public record ClassDTO(UUID classId, String name) {
    }

    public record ChildDTO(UUID childId, String fullName, ClassDTO learningClass) {
    }
}
