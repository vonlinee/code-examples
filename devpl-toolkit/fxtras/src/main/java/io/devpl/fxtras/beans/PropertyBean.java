package io.devpl.fxtras.beans;

import javafx.beans.property.Property;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * 使用Cglib代理为普通的JavaBean添加数据绑定支持
 * @param <T>
 */
public final class PropertyBean<T> implements MethodInterceptor {

    /**
     * 一定要是普通的JavaBean，属性不能是Property
     */
    private T bean;
    private Enhancer enhancer;
    private final Class<T> typeClass;

    PropertyBean(Class<T> type) {
        this.typeClass = type;
        init(typeClass);
    }

    @SuppressWarnings("unchecked")
    PropertyBean(T bean) {
        this.typeClass = (Class<T>) bean.getClass();
        this.bean = Objects.requireNonNull(bean, "java bean cannot be null!");
        init(typeClass);
    }

    private void init(Class<T> typeClass) {
        if (bean == null) {
            try {
                bean = typeClass.getConstructor().newInstance();
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                     NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        }
        this.enhancer = new Enhancer();
        this.enhancer.setSuperclass(bean.getClass());
        this.enhancer.setCallback(this);
    }

    public static <T> PropertyBean<T> of(Class<T> typeClass) {
        return new PropertyBean<>(typeClass);
    }

    public static <T> PropertyBean<T> of(T bean) {
        return new PropertyBean<>(bean);
    }

    private final Map<String, Property<Object>> bindingsMap = new HashMap<>(10);
    private final Map<String, Method> setters = new HashMap<>();

    /**
     * 双向绑定：字段的值随Property的变化而变化
     * @param filedName 字段名称
     * @param fieldType 字段值类型
     * @param property  属性对象
     * @param <V>       字段值泛型
     * @return this
     */
    @SuppressWarnings("unchecked")
    public <V> PropertyBean<T> bindBidirectional(String filedName, Class<V> fieldType, Property<V> property) {
        bindingsMap.put(filedName, (Property<Object>) property);
        return bind(filedName, fieldType, property);
    }

    /**
     * 单向绑定：字段的值随Property的变化而变化
     * @param filedName 字段名称
     * @param fieldType 字段值类型
     * @param property  属性对象
     * @param <V>       字段值泛型
     * @return this
     */
    public <V> PropertyBean<T> bind(String filedName, Class<V> fieldType, Property<V> property) {
        if (setters.containsKey(filedName)) {
            return this; // 避免重复添加
        }
        Method setterMethod = getSetterMethod(filedName, fieldType);
        if (setterMethod == null) {
            return this;
        }
        setters.put(filedName, setterMethod);
        property.addListener((observable, oldValue, newValue) -> {
            try {
                setters.get(filedName).invoke(bean, newValue);
            } catch (IllegalAccessException | InvocationTargetException e) {
                // throw new RuntimeException(e);
            }
        });
        return this;
    }

    private Method getSetterMethod(String fieldName, Class<?> fieldType) {
        Method method;
        try {
            method = typeClass.getMethod("set" + Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1), fieldType);
            method.setAccessible(true);
        } catch (NoSuchMethodException e) {
            return null;
        }
        return method;
    }

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        // set方法
        String methodName = method.getName();
        if (methodName.startsWith("set")) {
            String fieldName = Character.toLowerCase(methodName.charAt(3)) + methodName.substring(4);
            Property<Object> property = bindingsMap.get(fieldName);
            if (property != null) {
                property.setValue(args[0]);
            }
        }
        return method.invoke(bean, args);
    }

    @SuppressWarnings("unchecked")
    public T build() {
        return (T) enhancer.create();
    }

    public <V> void set(BiConsumer<T, V> column, V value) {
        column.accept(bean, value);
    }

    public <V> V get(Function<T, V> column) {
        return column.apply(this.bean);
    }
}
