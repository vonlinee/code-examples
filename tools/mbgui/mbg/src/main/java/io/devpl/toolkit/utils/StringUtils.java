package io.devpl.toolkit.utils;

import cn.hutool.core.util.StrUtil;

import javax.annotation.CheckForNull;
import javax.annotation.Nullable;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.StringJoiner;

/**
 * 统一工具类使用
 */
public final class StringUtils {

    private StringUtils() {
    }

    public static boolean isNullOrEmpty(@CheckForNull String string) {
        return string == null || string.isEmpty();
    }

    public static boolean hasText(@Nullable String string) {
        return org.springframework.util.StringUtils.hasText(string);
    }

    /**
     * 默认跳过null元素
     *
     * @param delimiter 分隔符
     * @param elements  元素列表
     * @return 拼接后的字符串
     */
    public static String join(CharSequence delimiter, boolean skipNull, CharSequence... elements) {
        StringJoiner joiner = new StringJoiner(delimiter);
        for (CharSequence cs : elements) {
            if (cs == null && skipNull) {
                continue;
            }
            joiner.add(cs);
        }
        return joiner.toString();
    }

    /**
     * 字符串拼接
     *
     * @param args 拼接对象数组
     * @return 拼接字符串
     */
    public static String concat(Object... args) {
        if (args == null || args.length == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < args.length; i++) {
            sb.append(args[i]);
        }
        return sb.toString();
    }

    /**
     * 中文UTF8编码
     *
     * @param src 源字符串
     * @return
     */
    public static String utf8Decode(String src) {
        src = URLDecoder.decode(src, StandardCharsets.UTF_8);
        return src;
    }

    public static boolean containsIgnoreCase(String parent, String child) {
        return parent.toLowerCase().contains(child.toLowerCase());
    }

    /**
     * 转为驼峰形式
     *
     * @param str 下划线形式
     * @return
     */
    public static String toCamelCase(String str) {
        return StrUtil.toCamelCase(str.toLowerCase());
    }
}
