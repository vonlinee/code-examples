package io.devpl.sdk.util;

import java.util.Map;

/**
 * 针对嵌套Map
 * @param <K>
 * @param <V>
 */
public abstract class FlatMap<K, V> implements Map<K, V> {

    private transient Map<K, V> map;
}
