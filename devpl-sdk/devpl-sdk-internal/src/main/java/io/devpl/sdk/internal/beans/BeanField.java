package io.devpl.sdk.internal.beans;

import java.lang.reflect.Modifier;

public class BeanField<V> implements Field<V>, FieldOperation<V> {

    private final String name;
    private V value;
    private String description;
    private final Class<V> type;
    private boolean accessible;

    public BeanField(String name, V value, Class<V> type) {
        this.name = name;
        this.value = value;
        this.type = type;
    }

    @Override
    public int modifier() {
        return Modifier.PRIVATE;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public String description() {
        return description;
    }

    @Override
    public void setAccessible(boolean accessible) {
        this.accessible = accessible;
    }

    @Override
    public boolean isAccessible() {
        return accessible;
    }

    @Override
    public Class<V> type() {
        return type;
    }

    @Override
    public String id() {
        return name;
    }

    @Override
    public void set(V value) {
        this.value = value;
    }

    @Override
    public V get() {
        return value;
    }

    @Override
    public void description(String description) {
        this.description = description;
    }
}
