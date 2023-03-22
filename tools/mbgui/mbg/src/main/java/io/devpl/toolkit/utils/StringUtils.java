package io.devpl.toolkit.utils;

import javax.annotation.Nullable;

/**
 * 统一工具类使用
 */
public final class StringUtils {

    private StringUtils() {
    }

    public static boolean hasText(@Nullable String string) {
        return org.springframework.util.StringUtils.hasText(string);
    }

    /**
     * 字符串拼接
     *
     * @param delimiter 分隔符
     * @param args      拼接对象
     * @return 拼接字符串
     */
    public static String concat(String delimiter, Object... args) {
        if (args == null || args.length == 0) {
            return "";
        }
        CharSequence[] elements = new CharSequence[args.length];
        for (int i = 0; i < args.length; i++) {
            elements[i] = String.valueOf(args[i]);
        }
        return String.join(delimiter, elements);
    }

    /**
     * 字符串拼接，以空格作为分隔符
     *
     * @param args 拼接对象
     * @return 拼接字符串
     */
    public static String concat(Object... args) {
        return concat("", args);
    }
}
