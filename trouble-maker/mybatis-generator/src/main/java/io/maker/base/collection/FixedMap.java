package io.maker.base.collection;

import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * TODO 换成数组实现而不是List,虽然List也是基于数组
 * 如果key为null则在数组第一个元素位置
 * Thread-Safe
 *
 * @param <K>
 * @param <V>
 */
public class FixedMap<K, V> {

    private final AtomicInteger cursor = new AtomicInteger(0);

    private final Object[] keys;
    private final Object[] values;

    private int size; //存放元素个数

    public FixedMap(int intialCapacity) {
        this.keys = new Object[intialCapacity];
        this.values = new Object[intialCapacity];
    }

    public int size() {
        return keys.length;
    }

    public boolean isEmpty() {
        return keys.length == 0;
    }

    public boolean containsKey(Object key) {
        return true;
    }

    public boolean containsValue(Object value) {
        return Arrays.binarySearch(values, value) > 0;
    }

    @SuppressWarnings("unchecked")
    public V get(Object key) {
        int i = Arrays.binarySearch(keys, key);
        return (V) values[i];
    }

    @SuppressWarnings("unchecked")
    public V put(K key, V value) {
        // Non-atomic operations on volatile fields are operations where the volatile field
        // is read and the value is used to update the volatile field. It is possible for the
        // value of the field to change between the read and write, making the operation possibly invalid.
        // In such cases it is better to surround the operation with a synchronized block or make
        // use of one of the Atomic** or Atomic**FieldUpdater classes from the java.util.concurrent.atomic package.
        // cursor = cursor + 1;
        Object oldValue = values[cursor.get()];
        int cursorIndex = cursor.addAndGet(1);
        synchronized (this) {
            ensureCapacityFixed();
            keys[cursorIndex] = key;
            values[cursorIndex] = value;
        }
        return (V) oldValue;
    }

    private void ensureCapacityFixed() {
        if (cursor.get() >= values.length) {
            throw new UnsupportedOperationException("the capacity of FixedMap is fixed");
        }
    }

    @SuppressWarnings("unchecked")
    public V remove(Object key) {
        int i = Arrays.binarySearch(keys, key);
        if (i < 0) {
            return null;
        }
        V value = (V) values[i];
        values[i] = null;
        return value;
    }

    public void putAll(Map<? extends K, ? extends V> m) {
        for (K key : m.keySet()) {
            put(key, m.get(key));
        }
    }

    public void clear() {
        for (int i = 0; i < values.length; i++) {
            keys[i] = null;
            values[i] = null;
        }
    }

    @SuppressWarnings("unchecked")
    public @NotNull
    Set<K> keySet() {
        return new HashSet<K>((Collection<? extends K>) Arrays.asList(keys));
    }

    @SuppressWarnings("unchecked")
    public @NotNull
    Collection<V> values() {
        return (Collection<V>) Arrays.asList(values);
    }
}
