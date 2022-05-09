package io.maker.base.lang.reflect;

import java.lang.reflect.Method;

public class Reflective {

    public static final ClassLoader SYSTEM_CLASS_LOADER = ClassLoader.getSystemClassLoader();

    /**
     * Reflection.getCallerClass()要求调用者必须有@CallerSensitive注解，并且必须有权限（
     * 由bootstrap class loader或者extension class loader加载的类）才可以调用。
     * 深度为0或小于0时，返回为Reflection类本身，否则为该深度的调用类
     * @return Class<?>  Reflection.getCallerClass();
     */
    public static Class<?> getCallerClass() {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        String callerClassName = null;
        StackTraceElement element = null;
        if (stackTrace.length > 2) {
            element = stackTrace[2];
        }
        if (element != null) {
            callerClassName = element.getClassName();
        }
        return forName(callerClassName);
    }

    public static Method getCallerMethod(String methodName) {
        return null;
    }

    @SuppressWarnings("unchecked")
    public static <T> Class<T> forName(String className, ClassLoader loader) {
        try {
            return (Class<T>) Class.forName(className, true, loader);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public static <T> Class<T> forName(String className) {
        try {
            return (Class<T>) Class.forName(className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Returns the class's class loader, or null if none.
    public static ClassLoader getClassLoader(Class<?> caller) {
        // This can be null if the VM is requesting it
        if (caller == null) {
            return null;
        }
        // Circumvent security check since this is package-private
        return caller.getClassLoader();
    }

    public static ClassLoader getThreadClassLoader() {
        return Thread.currentThread().getContextClassLoader();
    }

    public static void main(String[] args) {
        System.out.println(getCallerClass());
    }
}
