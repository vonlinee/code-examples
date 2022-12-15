package io.devpl.codegen.fxui.common.utils;

import java.util.LinkedHashMap;
import java.util.Map;

public final class DataObject {

    private final Map<String, Object> data;

    public DataObject(int initialCapacity) {
        this.data = new LinkedHashMap<>(initialCapacity);
    }

    public void put(String name, Object value) {
        this.data.put(name, value);
    }

    @SuppressWarnings("unchecked")
    public <V> V get(String name) {
        return (V) this.data.get(name);
    }

    @SuppressWarnings("unchecked")
    public <V> V get(String name, Class<V> requiredType) {
        if (requiredType == null) {
            throw new IllegalArgumentException("requiredType cannot be null");
        }
        return (V) this.data.get(name);
    }
}
