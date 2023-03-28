package io.devpl.toolkit.utils;

public class ClassUtils {

    /**
     * 如果是基本类型，直接传名字即可
     * 引用类型则需要写全类名
     *
     * @param name 类型名称
     * @return class
     */
    public static Class<?> forName(String name) {
        try {
            return org.springframework.util.ClassUtils.forName(name, ClassUtils.class.getClassLoader());
        } catch (ClassNotFoundException e) {
            // do nothing
        }
        return null;
    }
}
