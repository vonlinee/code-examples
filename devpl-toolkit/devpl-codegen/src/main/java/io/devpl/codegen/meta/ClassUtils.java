package io.devpl.codegen.meta;

public class ClassUtils {

    private static final String PACKAGE_SEPARATOR = ".";

    /**
     * 获取类名称
     *
     * @param fullClassName 全类名，比如 a.b.c.X
     * @return 类名称
     */
    public static String getName(String fullClassName) {
        final int index = fullClassName.lastIndexOf(PACKAGE_SEPARATOR);
        if (index > 0) {
            return fullClassName.substring(index + 1);
        }
        return "";
    }
}
