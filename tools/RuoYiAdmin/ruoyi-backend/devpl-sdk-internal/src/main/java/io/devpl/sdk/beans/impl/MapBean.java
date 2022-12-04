package io.devpl.sdk.beans.impl;

import io.devpl.sdk.OptionalTypedGetter;
import io.devpl.sdk.beans.DynamicBean;
import io.devpl.sdk.beans.impl.flexi.FlexiBean;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.*;

/**
 * 基于Map结构实现DynamicBean
 * 属性是动态的，可以在Map中随意添加和删除
 * 实现可以选择继承Map或者采用其它组合一个Map来存放实例数据
 * <p>
 * This class extends {@link HashMap}, allowing it to be used wherever a map is.
 * See {@link FlexiBean} for a map-like bean implementation that is more controlled.
 */
public interface MapBean extends DynamicBean, OptionalTypedGetter<String> {

    @Override
    default String getString(String key) {
        return String.valueOf(getObject(key));
    }

    @Override
    default Integer getInteger(String key) {
        Object value = getObject(key);
        if (value == null) {
            return null;
        }
        if (value instanceof String) {
            return Integer.parseInt((String) value);
        }
        return (Integer) value;
    }

    // TODO 待实现

    @Override
    default Short getShort(String key) {
        return null;
    }

    @Override
    default Boolean getBoolean(String key) {
        return null;
    }

    @Override
    default Long getLong(String key) {
        return null;
    }

    @Override
    default Character getCharacter(String key) {
        return null;
    }

    @Override
    default Float getFloat(String key) {
        return null;
    }

    @Override
    default Double getDouble(String key) {
        return null;
    }

    @Override
    default Byte getByte(String key) {
        return null;
    }

    @Override
    default BigDecimal getBigDecimal(String key) {
        return null;
    }

    @Override
    default Number getNumber(String key) {
        return null;
    }

    @Override
    default BigInteger getBigInteger(String key) {
        return null;
    }

    @Override
    default <E extends Enum<E>> E getEnum(Class<E> clazz, String key) {
        return null;
    }

    @Override
    default Date getDate(String key) {
        return null;
    }

    @Override
    default LocalDateTime getLocalDateTime(String key) {
        return null;
    }

    @Override
    default <V> V get(String key, Class<V> type) {
        return null;
    }

    @Override
    default <MK, V> Map<MK, V> getMap(String key) {
        return null;
    }

    @Override
    default <MK, V> Map<MK, V> getMap(String key, Map<MK, V> defaultValue) {
        return null;
    }

    @Override
    default <MK, V> Map<MK, V> getMap(String key, Class<MK> keyType, Class<V> valueType) {
        return null;
    }

    @Override
    default <V> Map<String, V> getMap(String key, Class<V> valueType) {
        return null;
    }

    @Override
    default <E> List<E> getList(String key) {
        return null;
    }

    @Override
    default <E> List<E> getList(String key, Class<E> elementType) {
        return null;
    }

    @Override
    default <E> Set<E> getSet(String key) {
        return null;
    }

    @Override
    default <E> Set<E> getSet(String key, Class<E> elementType) {
        return null;
    }
}
