package io.devpl.sdk.util;

import java.util.Map;

/**
 * 数据项
 */
public class DataItem implements Map.Entry<String, Object> {

    private final String name;
    private Object value;

    public DataItem(String name, Object value) {
        this.name = name;
        this.value = value;
    }

    @Override
    public String getKey() {
        return name;
    }

    @Override
    public Object getValue() {
        return value;
    }

    @Override
    public Object setValue(Object value) {
        Object oldValue = this.value;
        this.value = value;
        return oldValue;
    }
}
