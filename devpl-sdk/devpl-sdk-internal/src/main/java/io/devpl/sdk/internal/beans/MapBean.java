package io.devpl.sdk.internal.beans;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class MapBean implements DynamicBean, BeanOperation {

    HashMap<String, Object> map;
    private int size;
    private String name;

    private final Set<Field> fields = new HashSet<>();

    @Override
    public String name() {
        return name;
    }

    @Override
    public Field lookup(String fieldName) {
        return null;
    }

    @Override
    public void define(String fieldName, Class<?> type) {
        fields.add(new MapBeanField<>(fieldName, null, type));
    }

    @Override
    public void remove(String fieldName) {

    }
}
