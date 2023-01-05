package io.devpl.toolkit.framework.utils;

public class Utils {

    public static String objectToString(Object obj) {
        if (obj == null) {
            return null;
        }
        return obj.getClass().getName() + "@" + Integer.toHexString(obj.hashCode());
    }
}
