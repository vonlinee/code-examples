package io.devpl.toolkit.fxui.utils.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import javafx.beans.value.ObservableValue;
import org.hildan.fxgson.FxGson;

import java.io.IOException;

public class GsonConverter implements JSONConverter {

    private final Gson gson;

    public GsonConverter() {
        final GsonBuilder builder = new GsonBuilder();
        builder.serializeNulls(); // 不忽略null值
        // builder.registerTypeAdapter(Property.class, null);
        this.gson = FxGson.addFxSupport(builder)
                .create();
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
