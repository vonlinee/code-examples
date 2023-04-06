package io.devpl.toolkit.common;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

public class HashBeanMap implements BeanMap {

    private final Map<String, Object> map = new HashMap<>();

    @Override
    @SuppressWarnings("unchecked")
    public <V> V set(String key, V value) {
        return (V) map.put(key, value);
    }

    @Override
    public <V> V get(String key) {
        return null;
    }

    @Override
    public <V> V get(String key, V defaultValue) {
        return null;
    }

    @Override
    public <S, T> T map(String key, Function<? super S, T> mapping) {
        return null;
    }

    @Override
    public void forEach(Consumer<BeanField> consumer) {

    }
}
