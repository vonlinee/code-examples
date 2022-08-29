package io.devpl.sdk.support.utils;

import java.util.Collection;
import java.util.Map;
import java.util.function.Predicate;

public final class Values {

    //==================== isXxx START ===============================

    public static boolean isEmpty(CharSequence sequence) {
        return sequence == null || sequence.length() == 0;
    }

    public static boolean isEmpty(Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

    public static boolean isEmpty(Map<?, ?> map) {
        return map == null || map.isEmpty();
    }

    //==================== isXxx END ===============================

    //==================== whenXxx START ===============================

    /**
     * @param str
     * @param defaultValue notNull
     * @return
     */
    public static String whenEmpty(String str, String defaultValue) {
        return isEmpty(str) ? defaultValue : str;
    }

    public static <T> T when(T val, Predicate<T> test, T defaultVale) {
        return test.test(val) ? defaultVale : val;
    }

    //==================== whenXxx END ===============================
}
