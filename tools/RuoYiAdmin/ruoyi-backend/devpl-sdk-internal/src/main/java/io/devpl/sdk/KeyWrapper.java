package io.devpl.sdk;

import java.util.Objects;

public final class KeyWrapper {

    /**
     * 作为key的值
     */
    private final Object key;

    public KeyWrapper(Object valOfKey) {
        this.key = valOfKey;
    }

    public static <T> KeyWrapper valueOf(T val) {
        return new KeyWrapper(Objects.requireNonNull(val, "the value of key cannot be null!"));
    }

    public Object get() {
        return key;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        KeyWrapper key = (KeyWrapper) o;
        return key.key == this.key || key.key.equals(this.key);
    }
}
