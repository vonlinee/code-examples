package org.example.jackson.ext;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

public class JacksonExtensionTest {

	@Test
	public void test1() {
		ObjectMapper objectMapper = new ObjectMapper();
		SimpleModule module = new SimpleModule();
		module.addSerializer(MyClass.class, new MyClassSerializer());
		module.addDeserializer(MyClass.class, new MyClassDeserializer());
		objectMapper.registerModule(module);
	}
}

@Data
@AllArgsConstructor
@RequiredArgsConstructor
class MyClass {
    @JsonProperty("my_property")
    private String property;
}

class MyClassSerializer extends JsonSerializer<MyClass> {
    @Override
    public void serialize(MyClass value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();
        gen.writeStringField("my_property", value.getProperty());
        gen.writeEndObject();
    }
}

class MyClassDeserializer extends JsonDeserializer<MyClass> {
    @Override
    public MyClass deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        JsonNode jsonNode = p.getCodec().readTree(p);
        return new MyClass(jsonNode.get("my_property").asText());
    }
}
