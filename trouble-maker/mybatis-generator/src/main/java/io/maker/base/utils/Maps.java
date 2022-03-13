package io.maker.base.utils;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public final class Maps {

    /**
     * 获取值，如果发生类型转换异常，则提供默认值
     * @param map
     * @param key
     * @param optionalValue
     * @return
     */
    public static String getString(Map<String, Object> map, String key, String optionalValue) {
        Object value = map.get(key);
        try {
            return (String) value;
        } catch (ClassCastException exception) {
            if (value instanceof Number) {
                return String.valueOf(value);
            }
        }
        return optionalValue;
    }

    public static String getString(Map<String, Object> map, String key) {
        return getString(map, key, "");
    }

    /**
     * 打印Map
     * @param map
     */
    public static String println(Map<?, ?> map) {
        StringBuilder sb = new StringBuilder("{\n");
        for (Object key : map.keySet()) {
            sb.append("\t").append("\"").append(key).append("\":\"").append(map.get(key)).append("\",\n");
        }
        return sb.substring(0, sb.length()) + "\n";
    }

    public static <K, V> Map<K, V> doFilterKey(Map<K, V> map, Predicate<K> rule) {
        Map<K, V> newMap = new HashMap<>();
        map.keySet().stream().filter(rule).forEach(k -> {
            newMap.put(k, map.get(k));
        });
        return newMap;
    }

    public static <K, V> Map<K, V> doFilterValue(Map<K, V> map, Predicate<V> rule) {
        Map<K, V> newMap = new HashMap<>();
        map.forEach((k, v) -> {
            if (!rule.test(v)) {
                newMap.put(k, v);
            }
        });
        return newMap;
    }

    @SuppressWarnings("unchecked")
    public static <K> K[] keys(Map<K, ?> map) {
        return (K[]) map.keySet().toArray();
    }

    @SuppressWarnings("unchecked")
    public static <K> K[] values(Map<K, ?> map) {
        return (K[]) map.values().toArray();
    }

    /**
     * 拷贝Map元素，不影响原来的Map
     * @param <K>
     * @param <V>
     */
    public static class MapCopier<K, V> {

        private List<K> fromKeys;
        private List<K> toKeys;

        public MapCopier() {
            this.fromKeys = new ArrayList<>();
            this.toKeys = new ArrayList<>();
        }

        public MapCopier<K, V> addKey(K fromKey, K toKey) {
            fromKeys.add(fromKey);
            toKeys.add(toKey);
            return MapCopier.this;
        }

        public <T extends Map<K, V>> Map<K, V> copy(final T fromMap) {
            Map<K, V> newMap = new HashMap<>();
            for (int i = 0; i < fromKeys.size(); i++) {
                newMap.put(toKeys.get(i), fromMap.get(fromKeys.get(i)));
            }
            return newMap;
        }
    }

    public static <K, V> MapCopier<K, V> copier() {
        return new MapCopier<>();
    }

    public static void main(String[] args) {

    }
}
