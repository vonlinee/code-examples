package com.panemu.tiwulfx.utils;

import org.apache.commons.beanutils.MethodUtils;
import org.apache.commons.beanutils.NestedNullException;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.beanutils.PropertyUtilsBean;

import java.beans.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@SuppressWarnings("unchecked")
public class ClassUtils {

    private static final Logger log = Logger.getLogger(ClassUtils.class.getName());

    private static final Class<?>[] EMPTY_CLASS_PARAMETERS = new Class[0];
    private static final Class<?>[] LIST_CLASS_PARAMETER = new Class[]{java.util.List.class};

    @SuppressWarnings("unchecked")
    public static <T> T newInstance(Class<T> clazz) {
        final Constructor<?>[] ctors;
        try {
            ctors = Class.forName(clazz.getName()).getDeclaredConstructors();
        } catch (ClassNotFoundException e) {
            return null;
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


    /**
     * <p>Retrieve the property descriptors for the specified class,
     * introspecting and caching them the first time a particular bean class
     * is encountered.</p>
     *
     * <p><strong>FIXME</strong> - Does not work with DynaBeans.</p>
     * @param beanClass Bean class for which property descriptors are requested
     * @return the property descriptors
     * @throws IllegalArgumentException if <code>beanClass</code> is null
     */
    public static PropertyDescriptor[] getPropertyDescriptors(Class<?> beanClass) {
        if (beanClass == null) {
            throw new IllegalArgumentException("No bean class specified");
        }
        // Look up any cached descriptors for this bean class
        PropertyDescriptor[] descriptors = null;

        // Introspect the bean and cache the generated descriptors
        BeanInfo beanInfo;
        try {
            beanInfo = Introspector.getBeanInfo(beanClass);
        } catch (IntrospectionException e) {
            return (new PropertyDescriptor[0]);
        }
        descriptors = beanInfo.getPropertyDescriptors();
        if (descriptors == null) {
            descriptors = new PropertyDescriptor[0];
        }

        // ----------------- Workaround for Bug 28358 --------- START ------------------
        //
        // The following code fixes an issue where IndexedPropertyDescriptor behaves
        // Differently in different versions of the JDK for 'indexed' properties which
        // use java.util.List (rather than an array).
        //
        // If you have a Bean with the following getters/setters for an indexed property:
        //
        //     public List getFoo()
        //     public Object getFoo(int index)
        //     public void setFoo(List foo)
        //     public void setFoo(int index, Object foo)
        //
        // then the IndexedPropertyDescriptor's getReadMethod() and getWriteMethod()
        // behave as follows:
        //
        //     JDK 1.3.1_04: returns valid Method objects from these methods.
        //     JDK 1.4.2_05: returns null from these methods.
        //
        for (PropertyDescriptor propertyDescriptor : descriptors) {
            if (propertyDescriptor instanceof IndexedPropertyDescriptor) {
                IndexedPropertyDescriptor descriptor = (IndexedPropertyDescriptor) propertyDescriptor;
                String propName = descriptor.getName().substring(0, 1).toUpperCase() + descriptor.getName()
                        .substring(1);

                if (descriptor.getReadMethod() == null) {
                    String methodName = descriptor.getIndexedReadMethod() != null ? descriptor.getIndexedReadMethod()
                            .getName() : "get" + propName;
                    Method readMethod = MethodUtils.getMatchingAccessibleMethod(beanClass, methodName, EMPTY_CLASS_PARAMETERS);
                    if (readMethod != null) {
                        try {
                            descriptor.setReadMethod(readMethod);
                        } catch (Exception e) {
                            log.log(Level.SEVERE, "Error setting indexed property read method", e);
                        }
                    }
                }
                if (descriptor.getWriteMethod() == null) {
                    String methodName = descriptor.getIndexedWriteMethod() != null ? descriptor.getIndexedWriteMethod()
                            .getName() : "set" + propName;
                    Method writeMethod = MethodUtils.getMatchingAccessibleMethod(beanClass, methodName, LIST_CLASS_PARAMETER);
                    if (writeMethod == null) {
                        Method[] methods = beanClass.getMethods();
                        for (Method method : methods) {
                            if (method.getName().equals(methodName)) {
                                Class<?>[] parameterTypes = method.getParameterTypes();
                                if (parameterTypes.length == 1 && List.class.isAssignableFrom(parameterTypes[0])) {
                                    writeMethod = method;
                                    break;
                                }
                            }
                        }
                    }
                    if (writeMethod != null) {
                        try {
                            descriptor.setWriteMethod(writeMethod);
                        } catch (Exception e) {
                            log.log(Level.SEVERE, "Error setting indexed property write method", e);
                        }
                    }
                }
            }
        }
        // ----------------- Workaround for Bug 28358 ---------- END -------------------
        return (descriptors);
    }

    /**
     * <p>Set the value of the specified simple property of the specified bean,
     * with no type conversions.</p>
     *
     * <p>For more details see <code>PropertyUtilsBean</code>.</p>
     * @param bean  Bean whose property is to be modified
     * @param name  Name of the property to be modified
     * @param value Value to which the property should be set
     * @throws IllegalAccessException    if the caller does not have
     *                                   access to the property accessor method
     * @throws IllegalArgumentException  if <code>bean</code> or
     *                                   <code>name</code> is null
     * @throws IllegalArgumentException  if the property name is
     *                                   nested or indexed
     * @throws InvocationTargetException if the property accessor method
     *                                   throws an exception
     * @throws NoSuchMethodException     if an accessor method for this
     *                                   propety cannot be found
     * @see PropertyUtilsBean#setSimpleProperty
     */
    public static void setSimpleProperty(Object bean, String name, Object value) {
        try {
            PropertyUtils.setSimpleProperty(bean, name, value);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException exception) {
            throw new RuntimeException(exception);
        }
    }

    /**
     * <p>Return the value of the specified simple property of the specified
     * bean, with no type conversions.</p>
     *
     * <p>For more details see <code>PropertyUtilsBean</code>.</p>
     * @param bean Bean whose property is to be extracted
     * @param name Name of the property to be extracted
     * @return The property value
     * @throws IllegalAccessException    if the caller does not have
     *                                   access to the property accessor method
     * @throws IllegalArgumentException  if <code>bean</code> or
     *                                   <code>name</code> is null
     * @throws IllegalArgumentException  if the property name
     *                                   is nested or indexed
     * @throws InvocationTargetException if the property accessor method
     *                                   throws an exception
     * @throws NoSuchMethodException     if an accessor method for this
     *                                   propety cannot be found
     * @see PropertyUtilsBean#getSimpleProperty
     */
    public static <T> T getSimpleProperty(Object bean, String name)
            throws IllegalAccessException, InvocationTargetException,
            NoSuchMethodException {
        return (T) PropertyUtils.getSimpleProperty(bean, name);
    }


    /**
     * <p>Return the value of the (possibly nested) property of the specified
     * name, for the specified bean, with no type conversions.</p>
     *
     * <p>For more details see <code>PropertyUtilsBean</code>.</p>
     * @param bean Bean whose property is to be extracted
     * @param name Possibly nested name of the property to be extracted
     * @return the nested property value
     * @throws IllegalAccessException    if the caller does not have
     *                                   access to the property accessor method
     * @throws IllegalArgumentException  if <code>bean</code> or
     *                                   <code>name</code> is null
     * @throws NestedNullException       if a nested reference to a
     *                                   property returns null
     * @throws InvocationTargetException if the property accessor method throws an exception
     * @throws NoSuchMethodException     if an accessor method for this
     *                                   propety cannot be found
     * @see PropertyUtilsBean#getNestedProperty
     */
    public static <T> T getNestedProperty(Object bean, String name)
            throws IllegalAccessException, InvocationTargetException,
            NoSuchMethodException {
        return (T) PropertyUtils.getNestedProperty(bean, name);
    }


    /**
     * <p>Set the value of the specified property of the specified bean,
     * no matter which property reference format is used, with no
     * type conversions.</p>
     *
     * <p>For more details see <code>PropertyUtilsBean</code>.</p>
     * @param bean  Bean whose property is to be modified
     * @param name  Possibly indexed and/or nested name of the property
     *              to be modified
     * @param value Value to which this property is to be set
     * @throws IllegalAccessException    if the caller does not have
     *                                   access to the property accessor method
     * @throws IllegalArgumentException  if <code>bean</code> or
     *                                   <code>name</code> is null
     * @throws InvocationTargetException if the property accessor method
     *                                   throws an exception
     * @throws NoSuchMethodException     if an accessor method for this
     *                                   propety cannot be found
     * @see PropertyUtilsBean#setProperty
     */
    public static void setProperty(Object bean, String name, Object value)
            throws IllegalAccessException, InvocationTargetException,
            NoSuchMethodException {
        PropertyUtils.setProperty(bean, name, value);
    }
}
