package io.devpl.sdk.utils;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public abstract class TypeSafe {

    public static Class<?> getClass(Object obj) {
        if (obj == null) return null;
        return obj.getClass();
    }

    /**
     * 判断对象是否某个Class的实例对象
     * @param obj   对象
     * @param clazz Class对象
     * @return 如果obj为null，返回false
     */
    public static boolean isInstance(Object obj, Class<?> clazz) {
        return clazz.isInstance(obj);
    }

    public static boolean isAssignableFrom(Class<?> type, Class<?> clazz, boolean npeQuite) {
        if (clazz == null) {
            if (!npeQuite) {
                throw new NullPointerException("the class cannot be null!");
            }
            return false;
        }
        return clazz.isAssignableFrom(type);
    }


}
