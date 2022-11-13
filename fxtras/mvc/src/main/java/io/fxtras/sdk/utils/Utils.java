package io.fxtras.sdk.utils;

public class Utils {

    public static String toString(Object obj) {
        if (obj == null) return "null";
        return obj.getClass().getName() + "@" + Integer.toHexString(obj.hashCode());
    }

    public static void println(Object obj) {
        StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[2];
        System.out.println(stackTraceElement.toString() + " => " + obj.hashCode() + " " + toString(obj));
    }
}
