package io.devpl.sdk.internal.utils;

public final class Values {

    private Values() {
    }

    @SuppressWarnings("unchecked")
    public static <V> V safeCast(Object val, Class<V> type, V option, boolean throwException, String message) {
        try {
            if (val == null) return null;
            if (type != null) {
                if (val.getClass() == type || type.isInstance(val) || val.getClass().isAssignableFrom(type)) {
                    return (V) val;
                }
            }
            return (V) val;
        } catch (Exception ex) {
            if (throwException) throw new RuntimeException(message);
            return option;
        }
    }
}
