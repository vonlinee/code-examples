package io.devpl.sdk.beans;

import io.devpl.sdk.OptionalTypedGetter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.*;

/**
 * 基于Map实现一个动态Bean
 */
@SuppressWarnings("unchecked")
public class MapBean extends AbstractDynamicBean implements Map<String, Object>, OptionalTypedGetter<String>, Serializable, Cloneable {

    @Override
    public int size() {
        return fields.size();
    }

    @Override
    public boolean isEmpty() {
        return fields.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return fields.containsKey(key);
    }

    /**
     * TODO 修改
     * @param value 值
     * @return 存在/不存在
     */
    @Override
    public boolean containsValue(Object value) {
        return fields.containsValue(value);
    }

    @Override
    public Object get(Object key) {
        return this.get((String) key);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Object put(String key, Object value) {
        if (value == null) {
            throw new UnsupportedOperationException("value of this bean cannot be null");
        }
        return fields.put(key, new BeanField<>(key, value, (Class<Object>) value.getClass()));
    }

    @Override
    public Object remove(Object key) {
        return fields.remove(key);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void putAll(Map<? extends String, ?> m) {
        m.forEach((k, v) -> fields.put(k, new BeanField<>(k, v, (Class<Object>) v.getClass())));
    }

    @Override
    public void clear() {
        fields.clear();
    }

    @Override
    public Set<String> keySet() {
        return fields.keySet();
    }

    @Override
    public Collection<Object> values() {
        Collection<Object> values = new HashSet<>(fields.size());
        for (Field<Object> field : fields.values()) {
            values.add(field.getValue());
        }
        return values;
    }

    @Override
    public Set<Entry<String, Object>> entrySet() {
        Set<Entry<String, Object>> entrySet = new HashSet<>(fields.size());
        Set<Entry<String, Field<Object>>> entries = this.fields.entrySet();
        for (Entry<String, Field<Object>> fieldEntry : entries) {
            String fieldName = fieldEntry.getKey();
            Object value = fieldEntry.getValue().getValue();
            entrySet.add(new AbstractMap.SimpleEntry<>(fieldName, value));
        }
        return entrySet;
    }


    @Override
    public MapBean clone() {
        try {
            return (MapBean) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    @Override
    public Object getObject(String key) {
        return get(key);
    }

    @Override
    public String getString(String key) {
        Object o = get(key);
        if (o instanceof String) {
            return (String) o;
        }
        return null;
    }

    @Override
    public Integer getInteger(String key) {
        Object o = get(key);
        if (o instanceof Integer) {
            return (Integer) o;
        }
        return null;
    }

    @Override
    public Short getShort(String key) {
        Object o = get(key);
        if (o instanceof Short) {
            return (Short) o;
        }
        return null;
    }

    @Override
    public Boolean getBoolean(String key) {
        Object o = get(key);
        if (o instanceof Boolean) {
            return (Boolean) o;
        }
        return null;
    }

    @Override
    public Long getLong(String key) {
        Object o = get(key);
        if (o instanceof Long) {
            return (Long) o;
        }
        return null;
    }

    @Override
    public Character getCharacter(String key) {
        Object o = get(key);
        if (o instanceof Character) {
            return (Character) o;
        }
        return null;
    }

    @Override
    public Float getFloat(String key) {
        Object o = get(key);
        if (o instanceof Float) {
            return (Float) o;
        }
        return null;
    }

    @Override
    public Double getDouble(String key) {
        Object o = get(key);
        if (o instanceof Double) {
            return (Double) o;
        }
        return null;
    }

    @Override
    public Byte getByte(String key) {
        Object o = get(key);
        if (o instanceof Byte) {
            return (Byte) o;
        }
        return null;
    }

    @Override
    public BigDecimal getBigDecimal(String key) {
        Object o = get(key);
        if (o instanceof BigDecimal) {
            return (BigDecimal) o;
        }
        return null;
    }

    @Override
    public Number getNumber(String key) {
        Object o = get(key);
        if (o instanceof Number) {
            return (Number) o;
        }
        return null;
    }

    @Override
    public BigInteger getBigInteger(String key) {
        Object o = get(key);
        if (o instanceof BigInteger) {
            return (BigInteger) o;
        }
        return null;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <E extends Enum<E>> E getEnum(Class<E> clazz, String key) {
        Object o = get(key);
        if (o instanceof Enum) {
            return (E) o;
        }
        return null;
    }

    @Override
    public Date getDate(String key) {
        Object o = get(key);
        if (o instanceof Date) {
            return (Date) o;
        }
        return null;
    }

    @Override
    public LocalDateTime getLocalDateTime(String key) {
        Object o = get(key);
        if (o instanceof LocalDateTime) {
            return (LocalDateTime) o;
        }
        return null;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <V> V get(String key, Class<V> type) {
        Object o = get(key);
        if (o != null && type != null && type.isAssignableFrom(o.getClass())) {
            return (V) o;
        }
        return null;
    }

    @Override
    public <MK, V> Map<MK, V> getMap(String key) {
        Object o = get(key);
        if (o instanceof Map) {
            return (Map<MK, V>) o;
        }
        return null;
    }

    @Override
    public <MK, V> Map<MK, V> getMap(String key, Class<MK> keyType, Class<V> valueType) {
        Object o = get(key);
        if (o instanceof Map) {
            return (Map<MK, V>) o;
        }
        return null;
    }

    @Override
    public <V> Map<String, V> getMap(String key, Class<V> valueType) {
        return null;
    }

    @Override
    public <E> List<E> getList(String key) {
        return null;
    }

    @Override
    public <E> List<E> getList(String key, Class<E> elementType) {
        return null;
    }

    @Override
    public <E> Set<E> getSet(String key) {
        return null;
    }

    @Override
    public <E> Set<E> getSet(String key, Class<E> elementType) {
        return null;
    }
}
