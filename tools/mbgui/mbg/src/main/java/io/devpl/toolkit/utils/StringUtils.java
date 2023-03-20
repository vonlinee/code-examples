package io.devpl.toolkit.utils;

import javax.annotation.Nullable;

/**
 * 统一工具类使用
 */
public final class StringUtils {

    public static boolean hasText(@Nullable String string) {
        return org.springframework.util.StringUtils.hasText(string);
    }
}
