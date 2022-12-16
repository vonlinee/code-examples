package code.fxutils.core;

import java.util.LinkedHashMap;

public final class DataObject extends LinkedHashMap<String, Object> {

    public DataObject(int initialCapacity) {
        super(initialCapacity);
    }

    @SuppressWarnings("unchecked")
    public <V> V getValue(String name) {
        return (V) super.get(name);
    }

    @SuppressWarnings("unchecked")
    public <V> V getValue(String name, Class<V> requiredType) {
        if (requiredType == null) {
            throw new IllegalArgumentException("requiredType cannot be null");
        }
        return (V) super.get(name);
    }
}
