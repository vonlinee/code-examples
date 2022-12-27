package io.devpl.toolkit.fxui.utils;

public class JSONUtils {

    private static final ObjectJSONConverter converter;

    static {
        converter = new GsonObjectJSONConverter();
    }

    public static String toString(Object obj) {
        if (obj == null) {
            return "";
        }
        return converter.toJSONString(obj);
    }

    public static <T> T fromString(String jsonStr, Class<T> requiredType) {
        return converter.toObject(jsonStr, requiredType);
    }
}
