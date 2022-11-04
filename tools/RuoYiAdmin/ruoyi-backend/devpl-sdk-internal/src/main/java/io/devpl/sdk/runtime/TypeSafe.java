package io.devpl.sdk.runtime;

/**
 * 类型检查工具类
 */
public final class TypeSafe {

    private static final boolean DEFAULT_IGNORE_NPE = true;

    public static boolean isCompatible(Object val, Class<?> clazz) {
        return isCompatible(val.getClass(), clazz);
    }

    /**
     * 是否兼容类型
     * @param type1
     * @param type2
     * @return
     */
    public static boolean isCompatible(Class<?> type1, Class<?> type2) {
        return false;
    }

    public static void main(String[] args) {
        System.out.println(isAssignableFrom(Integer.class, int.class));

        // System.out.println(isCompatible(int.class, Integer.class));
    }

    public static Class<?> getClass(Object obj) {
        if (obj == null) return null;
        return obj.getClass();
    }

    /**
     * 判断对象是否某个Class的实例对象
     * @param obj   对象
     * @param clazz Class对象
     * @return 如果obj为null，返回false
     */
    public static boolean isInstance(Object obj, Class<?> clazz) {
        if (clazz == null) {
            throw new NullPointerException("class is null!");
        }
        return clazz.isInstance(obj);
    }

    public static boolean isAssignableFrom(Class<?> parent, Class<?> child) {
        return isAssignableFrom(parent, child, DEFAULT_IGNORE_NPE);
    }

    /**
     * 判断第二个class是否可赋值给第一个class
     * isAssignableFrom(null, null) => true
     * isAssignableFrom(X.class, null) => true
     * @param parent    父类型
     * @param child     子类型
     * @param ignoreNPE 忽略null带来的空指针，直接返回true/false
     * @return
     */
    public static boolean isAssignableFrom(Class<?> parent, Class<?> child, boolean ignoreNPE) {
        if (parent == null) {
            if (ignoreNPE) return false;
            throw new NullPointerException();
        }
        if (child == null) {
            // null 不可赋值给基础类型
            if (ignoreNPE) return !parent.isPrimitive();
            throw new NullPointerException();
        }
        return parent.isAssignableFrom(child);
    }
}
