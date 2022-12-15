package io.devpl.toolkit.fxui.common;

import java.util.HashMap;
import java.util.Map;

/**
 * 存储所有配置项
 */
public final class ConfigMap {

    private final Map<String, Object> configurations = new HashMap<>();

    public void add(String name, Object value) {
        configurations.put(name, value);
    }

    public int getInt(String name) {
        return (int) configurations.get(name);
    }

    public long getLong(String name) {
        return (long) configurations.get(name);
    }
}
