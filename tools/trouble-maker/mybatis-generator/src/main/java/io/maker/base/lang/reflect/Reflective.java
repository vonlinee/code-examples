package io.maker.base.lang.reflect;

import io.maker.base.utils.Array;
import org.checkerframework.checker.units.qual.A;

import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.TypeVariable;
import java.util.Arrays;
import java.util.HashMap;

public final class Reflective {

    /**
     * @param <T>
     * @param type
     * @return
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException
     * @throws SecurityException
     */
    @SuppressWarnings("unchecked")
    public static <T> T newInstance(Class<T> type) throws InstantiationException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException, SecurityException {
        Constructor<?>[] constructors = type.getDeclaredConstructors();
        if (constructors.length >= 1) {
            return (T) constructors[0].newInstance(new Object[]{});
        }
        return null;
    }

    public static <T> void setProperty(T benaInstance, String propertyName, String propertyValue) throws Exception {
        PropertyDescriptor propDesc = new PropertyDescriptor(propertyName, benaInstance.getClass());
        Method methodSetUserName = propDesc.getWriteMethod();
        Object invoke = methodSetUserName.invoke(benaInstance, propertyValue);
    }

    /**
     * 通过属性名获取对象的属性值
     *
     * @param obj
     * @param propertyName
     * @return
     * @throws Exception Object
     */
    public static <T> Object getProperty(T obj, String propertyName) throws Exception {
        PropertyDescriptor proDescriptor = new PropertyDescriptor(propertyName, obj.getClass());
        Method methodGetUserName = proDescriptor.getReadMethod();
        return methodGetUserName.invoke(obj);
    }

    private static void test() {
        String typeName = HashMap.class.getTypeName();
        TypeVariable<Class<HashMap>>[] typeParameters = HashMap.class.getTypeParameters();
        for (TypeVariable<Class<HashMap>> typeParameter : typeParameters) {
            System.out.println(typeParameter.getTypeName());
            System.out.println(typeParameter.getName());
            Class<HashMap> genericDeclaration = typeParameter.getGenericDeclaration();
        }
    }
}
