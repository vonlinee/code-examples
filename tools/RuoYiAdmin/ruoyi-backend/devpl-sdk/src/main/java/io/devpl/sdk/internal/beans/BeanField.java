package io.devpl.sdk.internal.beans;

import java.util.Map;

public class BeanField<V> implements Map.Entry<String, V> {

    final int hash;
    final String key;
    V value;
    BeanField<V> next;

    public BeanField(int hash, String key, V value, BeanField<V> next) {
        super();
        this.hash = hash;
        this.key = key;
        this.value = value;
        this.next = next;
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public V getValue() {
        return value;
    }

    @Override
    public V setValue(V value) {
        V oldValue = this.value;
        this.value = value;
        return oldValue;
    }

    @Override
    public String toString() {
        return key + "=" + value;
    }
}
