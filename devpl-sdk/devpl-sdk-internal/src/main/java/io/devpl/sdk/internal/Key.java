package io.devpl.sdk.internal;

import java.util.Objects;

public final class Key {

    private final Object valOfKey;

    public Key(Object valOfKey) {
        this.valOfKey = valOfKey;
    }

    public static <T> Key valueOf(T val) {
        return new Key(Objects.requireNonNull(val, "the value of key cannot be null!"));
    }

    public Object get() {
        return valOfKey;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Key key = (Key) o;
        return key.valOfKey == this.valOfKey || key.valOfKey.equals(this.valOfKey);
    }
}
