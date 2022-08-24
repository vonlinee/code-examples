package io.devpl.sdk.internal.beans;

public interface FieldOperation<V> {

    void set(V value);

    V getAndSet(V value);

    V get();
}
