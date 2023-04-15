package io.devpl.codegen.generator.template.impl;

import io.devpl.codegen.generator.template.TemplateArguments;

import java.util.HashMap;
import java.util.Map;

/**
 * 动态模板参数，适用于自定义模板
 */
public class DynamicTemplateArguments implements TemplateArguments {

    private final Map<String, Object> arguments = new HashMap<>();

    public final void set(String name, Object value) {
        arguments.put(name, value);
    }

    @SuppressWarnings("unchecked")
    public final <T> T get(String name, T placeholder) {
        if (placeholder == null) {
            return null;
        }
        Object value = arguments.get(name);
        if (value == null) {
            return placeholder;
        }
        if (placeholder.getClass().isAssignableFrom(value.getClass())) {
            placeholder = (T) value;
        }
        return placeholder;
    }

    public final void remove(String argName) {
        arguments.remove(argName);
    }

    public final boolean contains(String argName) {
        return arguments.containsKey(argName);
    }

    @Override
    public Map<String, Object> asMap() {
        return arguments;
    }
}
