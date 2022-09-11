package io.maker.base.lang;

import java.io.Serializable;

public class ValueWrapper implements Serializable {

    private static final long serialVersionUID = 1L;

    protected Object value;

    private final TypeMetaHolder holder;

    protected ValueWrapper(Object value) {
        this.value = value;
        this.holder = new TypeMetaHolder(value);
    }

    public static ValueWrapper wrap(Object val) {
        return new ValueWrapper(val);
    }

    public final boolean isNull() {
        return value == null;
    }

    public final <T> T get() {
        return holder.tryCast(value);
    }

    public final <T> T getOptional(T optionalValue) {
        try {
            return get();
        } catch (ClassCastException e) {
            return optionalValue;
        }
    }

    @Override
    public String toString() {
        return this.value == null ? "NULL" : this.value.toString();
    }
}
