package io.devpl.sdk.util;

import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * 通过key进行访问的常量池
 * 和ConstantPool相比不需要定义泛型
 *
 * @param <K> key类型
 * @param <E> 常量类型
 */
public abstract class KeyedEnumPool<K, E> {

    public final ConcurrentMap<K, E> enumerations = new ConcurrentHashMap<>();

    public E get(Object key) throws NoSuchElementException {
        return enumerations.get(key);
    }

    public abstract E put(K key, E instance);

    public boolean containsKey(K key) {
        return enumerations.containsKey(key);
    }

    public Collection<E> values() {
        return enumerations.values();
    }
}
