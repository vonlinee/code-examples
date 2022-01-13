package code.fxutils.core.util;

import java.util.Collection;
import java.util.Map;

public final class Assert {

    /*
     * =================== WHEN =================================
     * */

    public static <T> void whenNull(T target, String message) {
        if (target == null) {
            throw new RuntimeException(message);
        }
    }

    public static <T> void whenBlank(String target, String message) {
        if (target.length() == 0) {
            throw new RuntimeException(message);
        }
    }

    public static <T> void whenEmpty(Collection<?> collection, String message) {
        if (collection.isEmpty()) {
            throw new RuntimeException(message);
        }
    }

    public static <T> void whenNotExistKey(Map<T, ?> map, T key, String message) {
        if (!map.containsKey(key)) {
            throw new RuntimeException(message);
        }
    }

    /*
     * =================== WHEN =================================
     * */


    public static <T> void notNull(T target, String message) {
        notNull(target, false, message);
    }

    public static <T> void notNull(T target, boolean check, String message) {
        if (target == null) {
            if (check) {
                throw new RuntimeException(message);
            } else {
                throw new NullPointerException(message);
            }
        }
    }
}
