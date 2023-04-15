package io.devpl.codegen.api.gen.template.impl;

import io.devpl.codegen.api.gen.template.TemplateArguments;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 动态模板参数，适用于自定义模板
 */
public class TemplateArgumentsMap implements TemplateArguments {

    private final HashMap<String, Object> arguments = new HashMap<>();

    @Override
    public Object put(String key, Object value) {
        return arguments.put(key, value);
    }

    @Override
    public void putAll(Map<String, Object> map) {
        arguments.putAll(map);
    }

    @Override
    public Object get(Object key) {
        return arguments.get(key);
    }

    @Override
    public boolean containsKey(Object key) {
        return arguments.containsKey(key);
    }

    @Override
    public Set<String> keySet() {
        return arguments.keySet();
    }

    @Override
    public Object remove(Object key) {
        return arguments.remove(key);
    }

    @Override
    public Map<String, Object> asMap() {
        return arguments;
    }
}
