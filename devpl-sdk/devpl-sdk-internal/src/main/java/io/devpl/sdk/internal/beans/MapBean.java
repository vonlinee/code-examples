package io.devpl.sdk.internal.beans;

import java.util.*;
import java.util.regex.Pattern;

public class MapBean implements DynamicBean, BeanOperation {

    private int size;
    private String name;

    volatile Map<String, Object> data = Collections.emptyMap();// CSIGNORE

    /**
     * 字段集合
     */
    private final Set<Field<Object>> fields = new HashSet<>();

    @Override
    public String name() {
        return name;
    }

    public BeanField lookup(String fieldName) {
        BeanField<String> field = new BeanField<>("", "", String.class);
        return null;
    }

    @Override
    public <V> void define(String fieldName, Class<V> type) {

    }

    @Override
    public void remove(String fieldName) {

    }

    @Override
    public boolean equals(Bean obj) {
        return obj instanceof DynamicBean;
    }

    public <V> void set(String fieldName, V value) {
        define(fieldName, value.getClass());
    }

    public <V> V get(String fieldName) {
        return null;
    }

    public int size() {
        return data.size();
    }

    /**
     * Valid regex for keys.
     */
    private static final Pattern VALID_KEY = Pattern.compile("[a-zA-z_][a-zA-z0-9_]*");

    /**
     * Sets a property in this bean to the specified value.
     * <p>
     * This creates a property if one does not exist.
     * @param propertyName the property name, not empty
     * @param newValue     the new value, may be null
     * @return the old value of the property, may be null
     */
    public Object put(String propertyName, Object newValue) {
        if (!VALID_KEY.matcher(propertyName).matches()) {
            throw new IllegalArgumentException("Invalid key for FlexiBean: " + propertyName);
        }
        return dataWritable().put(propertyName, newValue);
    }


    //-----------------------------------------------------------------------

    /**
     * Gets the internal data map.
     * @return the data, not null
     */
    private Map<String, Object> dataWritable() {
        if (data == Collections.EMPTY_MAP) {
            data = new LinkedHashMap<>();
        }
        return data;
    }

    public Map<String, Object> toMap() {
        if (size() == 0) {
            return Collections.emptyMap();
        }
        return Collections.unmodifiableMap(new LinkedHashMap<>(data));
    }
}
