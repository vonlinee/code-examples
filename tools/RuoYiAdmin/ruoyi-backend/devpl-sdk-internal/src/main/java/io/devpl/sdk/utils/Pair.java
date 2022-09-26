package io.devpl.sdk.utils;

import javax.annotation.concurrent.NotThreadSafe;
import java.io.Serializable;
import java.util.Map;
import java.util.Objects;

/**
 * 键值对对象，只能在构造时传入键值
 * @param <K> 键类型
 * @param <V> 值类型
 * @since 0.0.1
 */
@NotThreadSafe
public class Pair<K, V> implements Map.Entry<K, V>, Serializable {

    private static final long serialVersionUID = 1L;

    private K key;
    private V value;

    /**
     * 构建{@code Pair}对象
     * @param <K>   键类型
     * @param <V>   值类型
     * @param key   键
     * @param value 值
     * @return {@code Pair}
     * @since 5.4.3
     */
    public static <K, V> Pair<K, V> of(K key, V value) {
        return new Pair<>(key, value);
    }

    /**
     * 构造
     * @param key   键
     * @param value 值
     */
    public Pair(K key, V value) {
        this.key = key;
        this.value = value;
    }

    /**
     * 获取键
     * @return 键
     */
    public K getKey() {
        return this.key;
    }

    public K setKey(K key) {
        K oldKey = this.key;
        this.key = key;
        return oldKey;
    }

    /**
     * 获取值
     * @return 值
     */
    public V getValue() {
        return this.value;
    }

    @Override
    public V setValue(V value) {
        V oldValue = this.value;
        this.value = value;
        return oldValue;
    }

    @Override
    public String toString() {
        return "Pair [key=" + key + ", value=" + value + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o instanceof Pair) {
            Pair<?, ?> pair = (Pair<?, ?>) o;
            return Objects.equals(getKey(), pair.getKey()) &&
                    Objects.equals(getValue(), pair.getValue());
        }
        return false;
    }

    @Override
    public int hashCode() {
        //copy from 1.8 HashMap.Node
        return Objects.hashCode(key) ^ Objects.hashCode(value);
    }
}
