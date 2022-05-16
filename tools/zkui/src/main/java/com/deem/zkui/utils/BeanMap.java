package com.deem.zkui.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BeanMap extends HashMap<String, Object> implements Map<String, Object> {

    private List<String> fieldNames;
    private List<Object> fieldValue;

    public BeanMap(int initialCapacity) {
        this.fieldNames = new ArrayList<>(initialCapacity);
        this.fieldValue = new ArrayList<>(initialCapacity);
    }

    @SuppressWarnings("unchecked")
    public <T> T getField(String name) {
        return (T) get(name);
    }

    @SuppressWarnings("unchecked")
    public <T> T setField(String name, Object value) {
        return (T) put(name, value);
    }
}
