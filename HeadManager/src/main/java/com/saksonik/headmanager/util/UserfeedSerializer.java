package com.saksonik.headmanager.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.saksonik.headmanager.dto.userfeed.UserfeedDTO;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class UserfeedSerializer extends JsonSerializer<UserfeedDTO> {
    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Override
    public void serialize(UserfeedDTO userfeedDTO, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
            throws IOException {
        jsonGenerator.writeStartObject();

        // Serialize userId
        jsonGenerator.writeStringField("userId", userfeedDTO.getUserId().toString());

        // Serialize roles
        jsonGenerator.writeFieldName("roles");
        jsonGenerator.writeStartArray();
        for (String role : userfeedDTO.getRoles()) {
            jsonGenerator.writeString(role);
        }
        jsonGenerator.writeEndArray();

        // Serialize teachingClasses
        if (userfeedDTO.getTeachingClasses() != null) {
            jsonGenerator.writeFieldName("teachingClasses");
            jsonGenerator.writeStartArray();
            for (UserfeedDTO.ClassToSubjectsDTO classToSubjects : userfeedDTO.getTeachingClasses()) {
                jsonGenerator.writeStartObject();
                jsonGenerator.writeStringField("classId", classToSubjects.classDTO().classId().toString());
                jsonGenerator.writeStringField("name", classToSubjects.classDTO().name());
                jsonGenerator.writeEndObject();

                jsonGenerator.writeStartArray();
                for (UserfeedDTO.SubjectDTO subject : classToSubjects.subjects()) {
                    jsonGenerator.writeStartObject();
                    jsonGenerator.writeStringField("subjectId", subject.subjectId().toString());
                    jsonGenerator.writeStringField("name", subject.name());
                    jsonGenerator.writeEndObject();
                }
                jsonGenerator.writeEndArray();
            }
            jsonGenerator.writeEndArray();
        } else {
            jsonGenerator.writeNullField("studyingClass");
        }

        // Serialize studyingClass
        if (userfeedDTO.getStudyingClass() != null) {
            jsonGenerator.writeFieldName("studyingClass");
            jsonGenerator.writeStartObject();
            jsonGenerator.writeStringField("classId", userfeedDTO.getStudyingClass().classId().toString());
            jsonGenerator.writeStringField("name", userfeedDTO.getStudyingClass().name());
            jsonGenerator.writeEndObject();
        } else {
            jsonGenerator.writeNullField("studyingClass");
        }

        // Serialize children
        if (userfeedDTO.getChildren() != null) {
            jsonGenerator.writeFieldName("children");
            jsonGenerator.writeStartArray();
            for (UserfeedDTO.ChildDTO childDTO : userfeedDTO.getChildren()) {
                jsonGenerator.writeStartObject();
                jsonGenerator.writeStringField("childId", childDTO.childId().toString());
                jsonGenerator.writeStringField("fullName", childDTO.fullName());

                jsonGenerator.writeFieldName("learningClass");
                jsonGenerator.writeStartObject();
                jsonGenerator.writeStringField("classId", childDTO.learningClass().classId().toString());
                jsonGenerator.writeStringField("name", childDTO.learningClass().name());
                jsonGenerator.writeEndObject();

                jsonGenerator.writeEndObject();
            }
            jsonGenerator.writeEndArray();
        } else {
            jsonGenerator.writeNullField("children");
        }

        // Serialize managedClasses
        if (userfeedDTO.getManagedClasses() != null) {
            jsonGenerator.writeFieldName("managedClasses");
            jsonGenerator.writeStartArray();
            for (UserfeedDTO.ClassDTO classDTO : userfeedDTO.getManagedClasses()) {
                jsonGenerator.writeStartObject();
                jsonGenerator.writeStringField("classId", classDTO.classId().toString());
                jsonGenerator.writeStringField("name", classDTO.name());
                jsonGenerator.writeEndObject();
            }
            jsonGenerator.writeEndArray();
        } else {
            jsonGenerator.writeNullField("managedClasses");
        }

        jsonGenerator.writeEndObject();
    }
}
