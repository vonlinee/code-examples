package code.fxutils.core.utils;

public class Value extends JavaType {

    protected final Object value;

    public Value(Object value) {
        super();
        this.value = value;
        this.typeClass = value != null ? value.getClass() : null;
    }

    @SuppressWarnings("unchecked")
    public final <T> T get(Class<T> type) {
        return (T) value;
    }

    public boolean isNull() {
        return this.value == null;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Value) {
            Value value = (Value) obj;
            if (this.value != null)
                return this.value.equals(value.value);
            else
                return value.value == null;
        }
        return false;
    }
}
