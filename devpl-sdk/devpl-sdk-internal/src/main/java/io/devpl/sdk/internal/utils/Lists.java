package io.devpl.sdk.internal.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * List集合工具类
 */
public final class Lists {

    private Lists() {}

    public static <T extends List<?>> List<?> wrap(T o) {
        return new ArrayList<>(o);
    }

    public static void main(String[] args) {

    }
}
