package io.devpl.toolkit.fxui.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonObjectJSONConverter implements ObjectJSONConverter {

    private final Gson gson;

    public GsonObjectJSONConverter() {
        this.gson = new GsonBuilder()
                .serializeNulls() // 不忽略null值
                .create();
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
