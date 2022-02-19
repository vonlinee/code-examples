package io.maker.base.collection;

import java.io.Serializable;
import java.util.*;

/**
 * TODO 换成数组实现而不是List,虽然List也是基于数组
 * 如果key为null则在数组第一个元素位置
 * @param <K>
 * @param <V>
 */
public class ArrayMap<K, V> implements Map<K, V>, RandomAccess, Serializable {

    private final ArrayList<K> keys;
    private final ArrayList<V> values;

    private int size; //存放元素个数

    public ArrayMap(int intialCapacity) {
        this.keys = new ArrayList<>(intialCapacity);
        this.values = new ArrayList<>(intialCapacity);
    }

    @Override
    public int size() {
        return keys.size();
    }

    @Override
    public boolean isEmpty() {
        return keys.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return keys.contains(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return values.contains(value);
    }

    @Override
    public V get(Object key) {
        int i = keys.indexOf(key);
        return values.get(i);
    }

    @Override
    public V put(K key, V value) {
        int i = 0;
        if (key != null) {
            i = size++;
            if (!(keys.add(key) || values.add(value))) {
                i = 0;
            }
        }
        return values.get(i);
    }

    @Override
    public V remove(Object key) {
        V v = null;
        if (key == null) {
            v = values.set(0, null);
        } else {
            int i = keys.indexOf(key);
            if (i > 0) { //存在
                keys.set(i, null);
                v = values.set(0, null);
                size--;
            }
        }
        return v;
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        for (K key : m.keySet()) {
            put(key, m.get(key));
        }
    }

    @Override
    public void clear() {
        keys.clear();
        values.clear();
    }

    @Override
    public Set<K> keySet() {
        return new HashSet<>(keys);
    }

    @Override
    public Collection<V> values() {
        return values;
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        throw new UnsupportedOperationException();
    }
}
