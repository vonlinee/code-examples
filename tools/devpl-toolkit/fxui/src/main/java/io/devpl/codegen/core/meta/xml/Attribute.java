package io.devpl.codegen.core.meta.xml;

import java.util.Objects;

/**
 * XML标签的属性
 */
public class Attribute {

    private final String name;

    private final String value;

    public Attribute(String name, String value) {
        this.name = Objects.requireNonNull(name);
        this.value = Objects.requireNonNull(value);
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }
}
