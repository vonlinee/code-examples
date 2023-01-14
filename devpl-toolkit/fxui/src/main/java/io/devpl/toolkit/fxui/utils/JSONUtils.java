package io.devpl.toolkit.fxui.utils;

public class JSONUtils {

    private static final JSONConverter converter;

    static {
        converter = new GsonConverter();
    }

    public static String toString(Object obj) {
        if (obj == null) {
            return "";
        }
        return converter.toJSONString(obj);
    }

    public static <T> T toObject(String jsonStr, Class<T> requiredType) {
        return converter.toObject(jsonStr, requiredType);
    }
}
