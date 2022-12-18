package io.devpl.toolkit.fxui.utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class ClassUtils {

    // getConstructor 方法入参是可变长参数列表，对应类中构造方法的入参类型，这里使用无参构造。
    // newInstance 返回的是泛型 T，取决于 clazz 的类型 Class<T>。这里直接用 Object 接收了。
    public static <T> T instantiate(Class<T> clazz) throws RuntimeException {
        try {
            return getConstructor(clazz, (Class<?>) null).newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException("failed to instantiate class " + clazz + " cause:", e);
        }
    }

    public static <T> Constructor<T> getConstructor(Class<T> clazz, Class<?>... parameterTypes) throws RuntimeException {
        Constructor<T> constructor;
        try {
            if (parameterTypes == null) {
                parameterTypes = new Class[0];
            }
            constructor = clazz.getDeclaredConstructor(parameterTypes);
            constructor.setAccessible(true);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
        return constructor;
    }

    /**
     * 两个数组是否相等
     * @param a1, a2
     * @return 数组是否相等
     */
    public static boolean equals(Object[] a1, Object[] a2) {
        if (a1 == null) {
            return a2 == null || a2.length == 0;
        }
        if (a2 == null) {
            return a1.length == 0;
        }
        if (a1.length != a2.length) {
            return false;
        }
        for (int i = 0; i < a1.length; i++) {
            if (a1[i] != a2[i]) {
                return false;
            }
        }
        return true;
    }
}
