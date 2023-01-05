package io.devpl.toolkit.fxui.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import javafx.beans.property.Property;
import javafx.beans.value.ObservableValue;

import java.io.IOException;
import java.text.ParseException;

public class GsonObjectJSONConverter implements ObjectJSONConverter {

    private final Gson gson;

    public GsonObjectJSONConverter() {
        final GsonBuilder builder = new GsonBuilder();
        builder.serializeNulls(); // 不忽略null值
        // builder.registerTypeAdapter(Property.class, null);
        this.gson = builder.create();
    }

    static class PropertyTypeAdapter<T> extends TypeAdapter<ObservableValue<T>> {

        @Override
        public void write(JsonWriter out, ObservableValue<T> value) throws IOException {
            if (value == null) {
                out.nullValue();
            } else {
                out.value((String) value.getValue());
            }
        }

        @Override
        public ObservableValue<T> read(JsonReader in) throws IOException {
            if (in.peek() == JsonToken.NULL) {
                in.nextNull();
                return null;
            }
            return null;
        }
    }

    @Override
    public String toJSONString(Object obj) {
        return gson.toJson(obj);
    }

    @Override
    public <T> T toObject(String jsonString, Class<T> type) {
        return gson.fromJson(jsonString, type);
    }
}
