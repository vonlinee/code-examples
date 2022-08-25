package io.devpl.sdk.internal.beans;

import java.lang.reflect.Modifier;

public interface FieldOperation<V> {

    int modifier();

    String name();

    void setAccessible(boolean accessible);

    boolean isAccessible();

    Class<V> type();
}
