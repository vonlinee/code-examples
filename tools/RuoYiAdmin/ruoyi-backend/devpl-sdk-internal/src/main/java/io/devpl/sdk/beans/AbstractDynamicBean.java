package io.devpl.sdk.beans;

import java.util.LinkedHashMap;
import java.util.Map;

@SuppressWarnings("unchecked")
abstract class AbstractDynamicBean implements DynamicBean, DynamicBeanAccess {

    protected final Map<String, Field<Object>> fields = new LinkedHashMap<>(10);

    @Override
    public <V> Field<V> getField(String name) {
        return (Field<V>) fields.get(name);
    }

    @Override
    public MetaBean meta() {
        throw new UnsupportedOperationException();
    }

    @Override
    public <V> V get(String name) {
        Field<?> field = fields.get(name);
        return field == null ? null : (V) field.getValue();
    }

    @Override
    public <V> V set(String name, V value) {
        if (containsField(name)) {
            return (V) fields.get(name).setValue(value);
        }
        return null;
    }

    @Override
    public int fieldCount() {
        return fields.size();
    }

    @Override
    public String[] fieldNames() {
        return fields.keySet().toArray(new String[0]);
    }

    @Override
    public String id() {
        return DynamicBean.super.id();
    }

    @Override
    public <V> boolean defineField(String name, Class<V> type) {
        if (fields.containsKey(name)) {
            return false;
        }
        fields.put(name, (Field<Object>) new BeanField<V>(name, null, type));
        return true;
    }

    @Override
    public <V> boolean addField(String name, V value, Class<V> type) {
        if (fields.containsKey(name)) {
            return false;
        }
        fields.put(name, (Field<Object>) new BeanField<V>(name, value, type));
        return true;
    }

    @Override
    public boolean removeField(String name) {
        if (!fields.containsKey(name)) {
            return true;
        }
        fields.remove(name);
        return true;
    }

    @Override
    public boolean containsField(String name) {
        return fields.containsKey(name);
    }
}
