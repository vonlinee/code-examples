package io.devpl.codegen.mbpg.util;

import io.devpl.sdk.util.StringUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

public final class ClassUtils {

    private ClassUtils() {
    }

    /**
     * getConstructor 方法入参是可变长参数列表，对应类中构造方法的入参类型，这里使用无参构造。
     * newInstance 返回的是泛型 T，取决于 clazz 的类型 Class<T>。这里直接用 Object 接收了。
     * 调用默认方法创建对象实例
     *
     * @param clazz Class对象
     * @return 创建的对象实例
     */
    public static <T> T instantiate(Class<T> clazz) throws RuntimeException {
        try {
            final Constructor<T> constructor = clazz.getConstructor();
            constructor.setAccessible(true);
            return constructor.newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException("failed to instantiate class " + clazz + " cause:", e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException("failed to instantiate class " + clazz + " cause: no default constructor in Class[" + clazz + "]", e);
        }
    }

    /**
     * 获取类名
     *
     * @param className className 全类名，格式aaa.bbb.ccc.xxx
     * @return ignore
     */
    public static String getSimpleName(String className) {
        return StringUtils.isBlank(className) ? null : className.substring(className.lastIndexOf(StringPool.DOT) + 1);
    }

    private static final Pattern CLASS_NAME_PATTERN = Pattern.compile("");

    public static boolean isQualifiedClassName(String str) {
        return false;
    }

    /**
     * 获取类的包名
     *
     * @param qualifiedClassName 全限定类名
     * @return
     */
    public static String getPackageName(String qualifiedClassName) {
        if (qualifiedClassName == null || qualifiedClassName.length() == 0) {
            return qualifiedClassName;
        }
        if (!qualifiedClassName.contains(".")) {
            return "";
        }
        return getSimpleName(qualifiedClassName);
    }

    /**
     * <p>
     * 请仅在确定类存在的情况下调用该方法
     * </p>
     *
     * @param name 类名称
     * @return 返回转换后的 Class
     */
    public static Class<?> forName(String name) {
        try {
            return Class.forName(name, false, getDefaultClassLoader());
        } catch (ClassNotFoundException e) {
            try {
                return Class.forName(name);
            } catch (ClassNotFoundException ex) {
                throw new RuntimeException("找不到指定的class！请仅在明确确定会有 class 的时候，调用该方法", e);
            }
        }
    }

    /**
     * 代理 class 的名称
     */
    private static final List<String> PROXY_CLASS_NAMES = Arrays.asList("net.sf.cglib.proxy.Factory"
            // cglib
            , "org.springframework.cglib.proxy.Factory"
            , "javassist.util.proxy.ProxyObject"
            // javassist
            , "org.apache.ibatis.javassist.util.proxy.ProxyObject");

    /**
     * 判断是否为代理对象
     *
     * @param clazz 传入 class 对象
     * @return 如果对象class是代理 class，返回 true
     */
    public static boolean isProxy(Class<?> clazz) {
        if (clazz != null) {
            for (Class<?> cls : clazz.getInterfaces()) {
                if (PROXY_CLASS_NAMES.contains(cls.getName())) {
                    return true;
                }
            }
        }
        return false;
    }


    /**
     * <p>
     * 获取当前对象的 class
     * </p>
     *
     * @param clazz 传入
     * @return 如果是代理的class，返回父 class，否则返回自身
     */
    public static Class<?> getUserClass(Class<?> clazz) {
        Objects.requireNonNull(clazz, "Class must not be null");
        return isProxy(clazz) ? clazz.getSuperclass() : clazz;
    }

    /**
     * Return the default ClassLoader to use: typically the thread context
     * ClassLoader, if available; the ClassLoader that loaded the ClassUtils
     * class will be used as fallback.
     * <p>Call this method if you intend to use the thread context ClassLoader
     * in a scenario where you clearly prefer a non-null ClassLoader reference:
     * for example, for class path resource loading (but not necessarily for
     * {@code Class.forName}, which accepts a {@code null} ClassLoader
     * reference as well).
     *
     * @return the default ClassLoader (only {@code null} if even the system
     * ClassLoader isn't accessible)
     * @see Thread#getContextClassLoader()
     * @see ClassLoader#getSystemClassLoader()
     * @since 3.3.2
     */
    public static ClassLoader getDefaultClassLoader() {
        ClassLoader cl = null;
        try {
            cl = Thread.currentThread().getContextClassLoader();
        } catch (Throwable ex) {
            // Cannot access thread context ClassLoader - falling back...
        }
        if (cl == null) {
            // No thread context class loader -> use class loader of this class.
            cl = ClassUtils.class.getClassLoader();
            if (cl == null) {
                // getClassLoader() returning null indicates the bootstrap ClassLoader
                try {
                    cl = ClassLoader.getSystemClassLoader();
                } catch (Throwable ex) {
                    // Cannot access system ClassLoader - oh well, maybe the caller can live with null...
                }
            }
        }
        return cl;
    }
}
