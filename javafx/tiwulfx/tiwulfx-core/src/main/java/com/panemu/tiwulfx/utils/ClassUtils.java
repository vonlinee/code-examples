package com.panemu.tiwulfx.utils;

import org.apache.commons.beanutils.PropertyUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

@SuppressWarnings("unchecked")
public class ClassUtils {

    /**
     * 创建新实例
     * @param clazz clazz
     * @return {@link T}
     */
    public static <T> T newInstance(Class<T> clazz) {
        Constructor<?>[] ctors;
        try {
            ctors = Class.forName(clazz.getName()).getDeclaredConstructors();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        Constructor<?> ctor = null;
        for (Constructor<?> constructor : ctors) {
            ctor = constructor;
            if (ctor.getGenericParameterTypes().length == 0) {
                break;
            }
        }
        if (ctor == null) {
            return null;
        }
        try {
            return (T) ctor.newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    public static <R> R getSimpleProperty(final Object bean, final String name) {
        try {
            return (R) PropertyUtils.getSimpleProperty(bean, name);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    public static <R> R getNestedProperty(final Object bean, final String name) {
        try {
            return (R) PropertyUtils.getNestedProperty(bean, name);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    public static void setSimpleProperty(final Object bean,
                                         final String name, final Object value) {
        try {
            PropertyUtils.setSimpleProperty(bean, name, value);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }
}
