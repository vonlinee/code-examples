package io.maker.base.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class CollectionUtils {

    public static <K> boolean existKey(K key, Map<K, ?> map) {
        return map.containsKey(key);
    }

    public static <K, V> HashMap<K, V> newHashMap(int intialCapacity) {
        if (intialCapacity < 0) {
        	throw new IllegalArgumentException("intialCapacity of HashMap cannot be less than zero!");
        }
        return new HashMap<>(intialCapacity);
    }

    public static <T> List<T> emptyArrayList() {
        return new ArrayList<T>(0);
    }

    public static <T> List<T> newArrayList(int intialCapacity) {
        return new ArrayList<T>(intialCapacity);
    }
}
