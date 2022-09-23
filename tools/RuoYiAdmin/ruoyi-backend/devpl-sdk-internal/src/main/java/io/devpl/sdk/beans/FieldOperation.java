package io.devpl.sdk.beans;

public interface FieldOperation<V> {

    int modifier();

    String name();

    void setAccessible(boolean accessible);

    boolean isAccessible();

    Class<V> type();

    void set(V value);

    V get();

    void description(String description);
}
