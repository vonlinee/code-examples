package code.fxutils.core.util;

import java.io.Serializable;

public abstract class Pair<T> implements Serializable {
    private T v1;
    private T v2;

    public Pair(T v1, T v2) {
        this.v1 = v1;
        this.v2 = v2;
    }

    public final void update(T v1, T v2) {
        this.v1 = v1;
        this.v2 = v2;
    }

    public final T get(boolean flag) {
        return flag ? v1 : v2;
    }

    public final T get() {
        return v1;
    }

    public static class StringPair extends Pair<String> {
        public StringPair(String v1, String v2) {
            super(v1, v2);
        }
    }

    public static StringPair ofString(String v1, String v2) {
        return new StringPair(v1, v2);
    }
}