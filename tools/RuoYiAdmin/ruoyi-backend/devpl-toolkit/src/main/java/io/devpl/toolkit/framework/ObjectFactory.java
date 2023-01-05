package io.devpl.toolkit.framework;

import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 对象工厂
 * 存放单例，或者多例对象
 */
public class ObjectFactory {
    private static final Map<Class<?>, Object> instances = new ConcurrentHashMap<>();
    private static final Map<Class<?>, WeakReference<Object>> weakReferenceInstances = new ConcurrentHashMap<>();

    /**
     * 创建可不被回收的单例模式,当没有对象引用，单例对象将被gc掉
     * @param className 类名
     * @return 实例对象
     * @throws RuntimeException 实例化对象异常
     */
    @SuppressWarnings("unchecked")
    public static <E> E getInstance(Class<E> className) {
        Object instance = instances.get(className);
        if (instance == null) {
            synchronized (ObjectFactory.class) {
                instance = instances.get(className);
                if (instance == null) {
                    instances.put(className, newInstance(className));
                }
            }
        }
        return (E) instance;
    }

    /**
     * 通过默认构造器实例化一个类
     * @param clazz 类
     * @return 对象实例
     * @throws RuntimeException 实例化失败
     */
    private static Object newInstance(Class<?> clazz) {
        try {
            return clazz.getConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                 NoSuchMethodException e) {
            throw new RuntimeException("cannot instantiate class " + clazz, e);
        }
    }

    /**
     * 创建可回收的单例模式,当没有对象引用，单例对象将被gc掉
     * @param className 类名
     * @return 对象实例
     */
    @SuppressWarnings("unchecked")
    public static <E> E getWeakInstance(Class<E> className) {
        WeakReference<Object> reference = weakReferenceInstances.get(className);
        Object instance = reference == null ? null : reference.get();
        if (instance == null) {
            synchronized (ObjectFactory.class) {
                reference = weakReferenceInstances.get(className);
                instance = reference == null ? null : reference.get();
                if (instance == null) {
                    try {
                        instance = className.getDeclaredConstructor().newInstance();
                    } catch (InstantiationException | IllegalAccessException | NoSuchMethodException |
                             InvocationTargetException e) {
                        e.printStackTrace();
                    }
                    weakReferenceInstances.put(className, new WeakReference<>(instance));
                }
            }
        }
        return (E) instance;
    }
}