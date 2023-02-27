package io.devpl.tookit.utils.json;

public class JSONUtils {

    static final JSONConverter gson = new GsonConverter();

    public static <T> T toObject(String json, Class<T> type) {
        return gson.toObject(json, type);
    }

    public static String toJSONString(Object obj) {
        return gson.toJSONString(obj);
    }
}
