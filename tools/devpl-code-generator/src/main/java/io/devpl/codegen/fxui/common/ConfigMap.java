package io.devpl.codegen.fxui.common;

import java.util.HashMap;
import java.util.Map;

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