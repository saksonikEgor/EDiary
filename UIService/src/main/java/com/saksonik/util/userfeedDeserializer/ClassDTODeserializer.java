package com.saksonik.util.userfeedDeserializer;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.saksonik.dto.userfeed.UserfeedDTO;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ClassDTODeserializer extends StdDeserializer<UserfeedDTO.ClassDTO> {
    private static final ObjectMapper MAPPER = new ObjectMapper();

    public ClassDTODeserializer() {
        this(null);
    }

    public ClassDTODeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public UserfeedDTO.ClassDTO deserialize(JsonParser parser, DeserializationContext ctx)
            throws IOException, JacksonException {
        JsonNode node = parser.getCodec().readTree(parser);

        UUID classId = UUID.fromString(node.get("classId").asText());
        String name = node.get("name").asText();

        return new UserfeedDTO.ClassDTO(classId, name);
    }
}
