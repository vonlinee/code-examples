package io.devpl.sdk.internal.beans;

public interface FieldValueAccess<V> {

    void set(V value);

    V get();
}
