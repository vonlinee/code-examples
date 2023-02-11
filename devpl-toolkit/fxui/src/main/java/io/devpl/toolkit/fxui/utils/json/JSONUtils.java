package io.devpl.toolkit.fxui.utils.json;

public class JSONUtils {

    static final JSONConverter gson = new GsonConverter();

    public static <T> T toObject(String json, Class<T> type) {
        return gson.toObject(json, type);
    }
}
