package io.devpl.toolkit.fxui.utils;

import com.google.gson.Gson;

public class JSONUtils {

    private static final Gson gson = new Gson();

    public static String toString(Object obj) {
        if (obj == null) {
            return "";
        }
        return gson.toJson(obj, obj.getClass());
    }

    public static <T> T fromString(String jsonStr, Class<T> requiredType) {
        return gson.fromJson(jsonStr, requiredType);
    }
}
