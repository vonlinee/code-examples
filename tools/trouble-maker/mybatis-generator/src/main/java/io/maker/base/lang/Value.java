package io.maker.base.lang;

import java.util.Objects;

/**
 * 包装值
 */
public class Value extends TypeInfoHolder {

    // 单例容器
    private static final Value valueContainer = new Value(null);

    protected Object value;

    public Value(Object value) {
        super();
        this.value = value;
        this.typeClass = value != null ? value.getClass() : null;
    }

    public static Value wrapNullable(Object value) {
        return new Value(value);
    }

    public static Value wrap(Object value) {
        return new Value(Objects.requireNonNull(value));
    }

    public static Value resue(Object value) {
        valueContainer.value = value;
        valueContainer.typeClass = value != null ? value.getClass() : null;
        return valueContainer;
    }

    public static Value resetAndPut(Object value) {
        valueContainer.reset();
        return resue(value);
    }

    public void reset() {
        this.value = null;
        this.typeClass = null;
    }

    @SuppressWarnings("unchecked")
    public final <T> T get() {
        return (T) value;
    }

    public final String getString() {
        return (String) value;
    }

    public final int getInt() {
        return (Integer) value;
    }

    public boolean isNull() {
        return this.value == null;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Value) {
            Value value = (Value) obj;
            if (this.value != null) return this.value.equals(value.value);
            else return value.value == null;
        }
        return false;
    }
}
