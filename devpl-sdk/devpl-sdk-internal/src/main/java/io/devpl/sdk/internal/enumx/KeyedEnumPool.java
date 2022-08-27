package io.devpl.sdk.internal.enumx;

import io.devpl.sdk.internal.NamingStrategy;

import java.util.NoSuchElementException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public abstract class KeyedEnumPool<K, E> implements EnumPool<E> {

    private NamingStrategy<E> keyNamingStrategy;

    private final ConcurrentMap<K, E> enumerations = new ConcurrentHashMap<>();

    public E get(K key) throws NoSuchElementException {
        return null;
    }

    public abstract void put(K key, E instance);

    public boolean contains(K key) {
        return enumerations.containsKey(key);
    }
}
