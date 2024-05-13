package com.saksonik.util.userfeedDeserializer;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.saksonik.dto.userfeed.UserfeedDTO;

import java.io.IOException;
import java.util.UUID;

public class SubjectDTODeserializer extends StdDeserializer<UserfeedDTO.SubjectDTO> {
    private static final ObjectMapper MAPPER = new ObjectMapper();

    public SubjectDTODeserializer() {
        this(null);
    }

    public SubjectDTODeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public UserfeedDTO.SubjectDTO deserialize(JsonParser parser, DeserializationContext ctx)
            throws IOException, JacksonException {
        JsonNode node = parser.getCodec().readTree(parser);

        UUID subjectId = UUID.fromString(node.get("subjectId").asText());
        String name = node.get("name").asText();

        return new UserfeedDTO.SubjectDTO(subjectId, name);
    }
}
