package com.panemu.tiwulfx.utils;

import org.apache.commons.beanutils.PropertyUtils;

import java.lang.reflect.InvocationTargetException;

public class ClassUtils {

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
