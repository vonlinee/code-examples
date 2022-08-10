package io.maker.base.lang;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

/**
 * 基于Map结构的JavaBean
 * 所有方法都可能抛出java.lang.ClassCastException
 */
public final class MapBean extends HashMap<String, Object> implements Map<String, Object> {

    public MapBean(int initialCapacity, float loadFactor) {
        super(initialCapacity, loadFactor);
    }

    public MapBean(int initialCapacity) {
        super(initialCapacity);
    }

    public MapBean() {
        super();
    }

    public MapBean(Map<? extends String, ?> m) {
        super(m);
    }

    public String getString(String key) {
        return (String) super.get(key);
    }

    public String getString(String key, String optionalString) {
        Object value = get(key);
        return value == null ? optionalString : (String) value;
    }

    public Integer getInteger(String key) {
        return (Integer) super.get(key);
    }

    public Integer getInteger(String key, Integer optionalInteger) {
        Object value = get(key);
        return value == null ? optionalInteger : (Integer) value;
    }

    public Double getDouble(String key) {
        return (Double) super.get(key);
    }

    public Date getDate(String key) {
        return (Date) super.get(key);
    }

    public LocalDate getLocalDate(String key) {
        return (LocalDate) super.get(key);
    }

    public LocalDateTime getLocalDateTime(String key) {
        return (LocalDateTime) super.get(key);
    }

    public Timestamp getTimestamp(String key) {
        return (Timestamp) super.get(key);
    }

    public Long getLong(String key) {
        return (Long) super.get(key);
    }

    public Byte getByte(String key) {
        return (Byte) super.get(key);
    }

    public Object getObject(String key) {
        return super.get(key);
    }

    @SuppressWarnings("unchecked")
    public <T> T getValue(String key) {
        return (T) super.get(key);
    }

    @SuppressWarnings("unchecked")
    public <T> T getValue(String key, Class<T> valueType) {
        return (T) super.get(key);
    }

    @SuppressWarnings("unchecked")
    public <T> List<T> getList(String key) {
        return (List<T>) super.get(key);
    }

    @SuppressWarnings("unchecked")
    public <M, N> Map<M, N> getMap(String key) {
        return (Map<M, N>) super.get(key);
    }

    @SuppressWarnings("unchecked")
    public <M> Set<M> getSet(String key) {
        return (Set<M>) super.get(key);
    }
}
