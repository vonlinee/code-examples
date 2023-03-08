package sample.java8.multithread.unsafe;

import sun.reflect.Reflection;

import java.lang.reflect.Field;

/**
 * Unsafe工具类（仅支持JDK8）
 * https://tech.meituan.com/2019/02/14/talk-about-java-magic-class-unsafe.html
 * @author Administrator
 * @since JDK8
 */
public class UnsafeOps {

    @SuppressWarnings("restriction")
    private static sun.misc.Unsafe UNSAFE;

    private static final Object lock = new Object();

    static {
        UNSAFE = getUnsafe();
    }

    /**
     * 不能直接通过 UNSAFE = sun.misc.Unsafe.getUnsafe(); 获取
     * @return Unsafe
     */
    @SuppressWarnings("restriction")
    private static sun.misc.Unsafe getUnsafe() {
        if (UNSAFE == null) {
            synchronized (lock) {
                if (UNSAFE == null) {
                    try {
                        Field field = sun.misc.Unsafe.class.getDeclaredField("theUnsafe");
                        field.setAccessible(true);
                        UNSAFE = (sun.misc.Unsafe) field.get(null);
                    } catch (NoSuchFieldException | IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return UNSAFE;
    }

    @SuppressWarnings({
            "restriction", "unused"
    })
    public static sun.misc.Unsafe getUnsafeInstance() {
        Class<?> var0 = Reflection.getCallerClass();
        if (!sun.misc.VM.isSystemDomainLoader(var0.getClassLoader())) {
            throw new SecurityException("Unsafe");
        } else {
            // return theUnsafe;  //返回单例Unsafe实例
            return null;
        }
    }

    /**
     * @param clazz
     * @param fieldName
     * @param <T>
     * @return
     */
    public static <T> long objectFieldOffset(Class<T> clazz, String fieldName) {
        try {
            return UNSAFE.objectFieldOffset(clazz.getDeclaredField(fieldName));
        } catch (Exception e) {
            throw new Error(e);
        }
    }

    public static boolean compareAndSwapObject(Object obj, long offset, Object expectedValue, Object newValue) {
        return UNSAFE.compareAndSwapObject(obj, offset, expectedValue, newValue);
    }

    public static void putOrderedInt(Object obj, long offset, int val) {
        UNSAFE.putOrderedInt(obj, offset, val);
    }
}
