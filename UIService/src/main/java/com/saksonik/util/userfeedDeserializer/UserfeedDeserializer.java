package com.saksonik.util.userfeedDeserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.saksonik.dto.userfeed.UserfeedDTO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class UserfeedDeserializer extends StdDeserializer<UserfeedDTO> {
    private static final ObjectMapper MAPPER = new ObjectMapper();

    public UserfeedDeserializer() {
        this(null);
    }

    public UserfeedDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public UserfeedDTO deserialize(JsonParser parser, DeserializationContext ctx)
            throws IOException {
        JsonNode node = parser.getCodec().readTree(parser);

        UUID userId = UUID.fromString(node.get("userId").asText());
        List<String> roles = MAPPER.readValue(
                node.get("roles").toPrettyString(),
                new TypeReference<>() {
                }
        );
        List<UserfeedDTO.ClassToSubjectsDTO> teachingClasses;
        UserfeedDTO.ClassDTO studyingClass = null;
        List<UserfeedDTO.ChildDTO> children = null;
        List<UserfeedDTO.ClassDTO> managedClasses = null;


        if (node.get("teachingClasses") != null) {
            teachingClasses = new ArrayList<>();
            JsonNode teachingClassesNode = node.get("teachingClasses");

            Iterator<JsonNode> elements = teachingClassesNode.elements();
            AtomicInteger i = new AtomicInteger();
            elements.forEachRemaining(element -> {
                try {
                    if (i.get() % 2 == 0) {
                        UserfeedDTO.ClassDTO c = MAPPER.readValue(
                                element.toPrettyString(),
                                new TypeReference<>() {
                                }
                        );

                        UserfeedDTO.ClassToSubjectsDTO classToSubjectsDTO = new UserfeedDTO.ClassToSubjectsDTO();
                        classToSubjectsDTO.setClassDTO(c);
                        teachingClasses.add(classToSubjectsDTO);
                    } else {
                        List<UserfeedDTO.SubjectDTO> subjects = MAPPER.readValue(
                                element.toPrettyString(),
                                new TypeReference<>() {
                                }
                        );

                        teachingClasses.getLast()
                                .setSubjects(subjects);
                    }
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
                i.getAndIncrement();
            });
        } else {
            teachingClasses = null;
        }

        if (node.get("studyingClass") != null) {
            studyingClass = MAPPER.readValue(
                    node.get("studyingClass").toPrettyString(),
                    new TypeReference<>() {
                    }
            );
        }
        if (node.get("children") != null) {
            children = MAPPER.readValue(
                    node.get("children").toPrettyString(),
                    new TypeReference<>() {
                    }
            );
        }
        if (node.get("managedClasses") != null) {
            managedClasses = MAPPER.readValue(
                    node.get("managedClasses").toPrettyString(),
                    new TypeReference<>() {
                    }
            );
        }

        return new UserfeedDTO(
                userId,
                roles,
                teachingClasses,
                studyingClass,
                children,
                managedClasses
        );
    }
}
