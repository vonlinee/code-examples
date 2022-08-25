package io.devpl.sdk.internal.beans;

public interface BeanFieldAcess<V> {
    void set(String fieldName, V value);

    V get(String fieldName);
}
