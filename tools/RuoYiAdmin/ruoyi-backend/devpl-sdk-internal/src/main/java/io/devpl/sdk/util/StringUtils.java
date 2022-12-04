package io.devpl.sdk.util;

import java.util.function.Function;
import java.util.function.Supplier;

public final class StringUtils {

    public static String substring(String s, String b, char c) {
        int i = b.lastIndexOf(c);
        return s.substring(i);
    }

    public static String substring(String src, Supplier<Integer> startIndexSupplier) {
        return src.substring(startIndexSupplier.get());
    }

    public static <T> String substring(String src, Function<T, Integer> startIndexSupplier, T startIndex) {
        return src.substring(startIndexSupplier.apply(startIndex));
    }
}
