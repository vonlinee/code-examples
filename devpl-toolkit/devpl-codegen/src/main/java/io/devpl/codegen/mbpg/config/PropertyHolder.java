package io.devpl.codegen.mbpg.config;

import java.util.Hashtable;

/**
 * 封装一个 Properties
 */
public abstract class PropertyHolder {

    private final Hashtable<String, Object> properties;

    protected PropertyHolder() {
        properties = new Hashtable<>();
    }

    public void setProperty(String name, Object value) {
        properties.put(name, value);
    }

    @SuppressWarnings("unchecked")
    public <V> V getProperty(String name) {
        return (V) properties.get(name);
    }

    public String getString(String name, String defaultValue) {
        Object value = properties.get(name);
        return value == null ? defaultValue : (String) value;
    }
}