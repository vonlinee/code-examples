package io.devpl.sdk.beans;

@SuppressWarnings("unchecked")
public class BeanField<V> implements Field<V> {

    private final String name;
    private V value;
    private Class<V> type;

    public BeanField(String name, V value, Class<V> type) {
        this.name = name;
        this.value = value;
        this.type = type;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public V getValue() {
        return this.value;
    }

    @Override
    public V setValue(Object value) {
        V old = this.value;
        this.value = (V) value;
        return old;
    }

    @Override
    public Class<V> getType() {
        return type == null ? (Class<V>) Object.class : (this.value == null ? (Class<V>) Object.class : (Class<V>) this.value.getClass());
    }
}
