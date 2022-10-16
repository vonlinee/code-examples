package org.greenrobot.eventbus.ext;

import java.io.Serializable;
import java.util.*;

/**
 * 携带数据，以Map形式存储
 */
public final class Bundle implements Serializable {

    private final Map<String, Object> map = new HashMap<>();

    public static Bundle create() {
        return new Bundle();
    }

    public Bundle put(String name, Object value) {
        map.put(name, value);
        return this;
    }

    @SuppressWarnings("unchecked")
    private <T, V> T tryCast(V val, T defaultValue) {
        try {
            return (T) val;
        } catch (ClassCastException exception) {
            return defaultValue;
        }
    }

    public String getString(String name) {
        return tryCast(map.get(name), "");
    }

    public Integer getInteger(String name) {
        return tryCast(map.get(name), -1);
    }

    public Boolean getBoolean(String name) {
        return tryCast(map.get(name), Boolean.FALSE);
    }

    public <T> List<T> getList(String name, Class<T> elementType) {
        return tryCast(map.get(name), Collections.emptyList());
    }

    public <K, V> Map<K, V> getMap(String name, Class<K> mapKeyType, Class<V> mapValType) {
        return tryCast(map.get(name), Collections.emptyMap());
    }

    public <E> Set<E> getSet(String name, Class<E> elementType) {
        return tryCast(map.get(name), Collections.emptySet());
    }
}
