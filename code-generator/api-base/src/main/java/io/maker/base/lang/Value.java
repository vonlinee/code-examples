package io.maker.base.lang;

public class Value extends JavaType {

    protected final Object value;
    
    public Value(Object value) {
    	super();
        this.value = value;
        this.typeClass = value != null ? value.getClass() : null;
    }

    public static Value of(Object value) {
        return new Value(value);
    }
    
	@SuppressWarnings("unchecked")
    public final <T> T get(Class<T> type) {
        return (T) value;
    }

    public boolean isNull() {
        return this.value == null;
    }
}
