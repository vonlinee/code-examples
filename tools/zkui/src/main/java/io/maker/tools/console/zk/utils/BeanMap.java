package io.maker.tools.console.zk.utils;

import java.util.*;

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
        if (!fieldNames.contains(name)) {
            throw new NullPointerException("不存在" + name);
        }
        return (T) put(name, value);
    }

    @SuppressWarnings("unchecked")
    public <T> T putField(String name, Object value) {
        return (T) put(name, value);
    }

}
