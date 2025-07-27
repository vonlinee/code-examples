package org.example;

import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ReflectionTest {

    public static void main(String[] args) throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Class<?> unsafeClass = Class.forName("sun.misc.Unsafe");
        Field field = unsafeClass.getDeclaredField("theUnsafe");
        field.setAccessible(true);
        Unsafe unsafe = (Unsafe) field.get(null);

        Method putObjectVolatile =
                unsafeClass.getDeclaredMethod("putObjectVolatile", Object.class, long.class, Object.class);
        Method staticFieldOffset = unsafeClass.getDeclaredMethod("staticFieldOffset", Field.class);

        Class<?> loggerClass = Class.forName("jdk.internal.module.IllegalAccessLogger");
        Field loggerField = loggerClass.getDeclaredField("logger");
        Long offset = (Long) staticFieldOffset.invoke(unsafe, loggerField);
        putObjectVolatile.invoke(unsafe, loggerClass, offset, null);
    }
}
