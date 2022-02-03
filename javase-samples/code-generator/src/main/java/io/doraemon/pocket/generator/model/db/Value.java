package io.doraemon.pocket.generator.model.db;

import java.util.HashMap;
import java.util.Map;

/**
 * 任意值
 */
public class Value extends JavaType {

    private static final Map<String, Value> table = new HashMap<>();

    private final Object value;

    private Value(Object value) {
        this.value = value;
        this.classType = value != null ? value.getClass() : Void.class;
    }

    public static Value of(Object value) {
        return new Value(value);
    }

    public static Value register(String name, Object value) {
        Value val = new Value(value);
        table.put(name, val);
        return val;
    }

    public Value lookup(String name) {
        return table.get(name);
    }

    @SuppressWarnings("unchecked")
    public final <T> T get(T type) {
        return (T) value;
    }

    public boolean isNull() {
        return this.value == null;
    }
}
