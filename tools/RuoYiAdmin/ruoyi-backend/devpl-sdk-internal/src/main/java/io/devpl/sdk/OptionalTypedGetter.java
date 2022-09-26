package io.devpl.sdk;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 提供默认值，不对默认值进行检查，由调用方保证其是否符合要求
 *
 * @param <K> key类型
 */
public interface OptionalTypedGetter<K> extends TypedGetter<K> {

    default Object getObject(K key, Object placeholder) {
        Object val = getObject(key);
        return val == null ? placeholder : val;
    }

    default String getString(K key, String placeholder) {
        String val = getString(key);
        return val == null ? placeholder : val;
    }

    default Integer getInteger(K key, Integer placeholder) {
        Integer val = getInteger(key);
        return val == null ? placeholder : val;
    }

    default Short getShort(K key, Short placeholder) {
        Short val = getShort(key);
        return val == null ? placeholder : val;
    }

    default Boolean getBoolean(K key, Boolean placeholder) {
        Boolean val = getBoolean(key);
        return val == null ? placeholder : val;
    }

    default Long getLong(K key, Long placeholder) {
        Long val = getLong(key);
        return val == null ? placeholder : val;
    }

    default Character getCharacter(K key, Character placeholder) {
        Character val = getCharacter(key);
        return val == null ? placeholder : val;
    }

    default Float getFloat(K key, Float placeholder) {
        Long val = getLong(key);
        return val == null ? placeholder : val;
    }

    default Double getDouble(K key, Double placeholder) {
        Long val = getLong(key);
        return val == null ? placeholder : val;
    }

    default Byte getByte(K key, Byte placeholder) {
        Byte val = getByte(key);
        return val == null ? placeholder : val;
    }

    default BigDecimal getBigDecimal(K key, BigDecimal placeholder) {
        BigDecimal val = getBigDecimal(key);
        return val == null ? placeholder : val;
    }

    default Number getNumber(K key, Number placeholder) {
        Number val = getNumber(key);
        return val == null ? placeholder : val;
    }

    default BigInteger getBigInteger(K key, BigInteger placeholder) {
        BigInteger val = getBigInteger(key);
        return val == null ? placeholder : val;
    }

    default <E extends Enum<E>> E getEnum(Class<E> clazz, K key, E placeholder) {
        E val = getEnum(clazz, key);
        return val == null ? placeholder : val;
    }

    default Date getDate(K key, Date placeholder) {
        Date val = getDate(key);
        return val == null ? placeholder : val;
    }

    default LocalDateTime getLocalDateTime(K key, LocalDateTime placeholder) {
        LocalDateTime val = getLocalDateTime(key);
        return val == null ? placeholder : val;
    }

    default <V> V get(K key, Class<V> type, V placeholder) {
        V val = get(key, type);
        return val == null ? placeholder : val;
    }

    default <MK, V> Map<MK, V> getMap(K key, Map<MK, V> optional) {
        Map<MK, V> map = getMap(key);
        return map == null ? optional : map;
    }

    default <MK, V> Map<MK, V> getMap(K key, Class<MK> keyType, Class<V> valueType, Map<MK, V> optional) {
        Map<MK, V> map = getMap(key, keyType, valueType);
        return map == null ? optional : map;
    }

    default <V> Map<String, V> getMap(K key, Class<V> valueType, Map<String, V> optional) {
        Map<String, V> map = getMap(key, valueType);
        return map == null ? optional : map;
    }

    default <E> List<E> getList(K key, List<E> optional) {
        List<E> list = getList(key);
        return list == null ? optional : list;
    }

    default <E> List<E> getList(K key, Class<E> elementType, List<E> optional) {
        List<E> list = getList(key, elementType);
        return list == null ? optional : list;
    }

    default <E> Set<E> getSet(K key, Set<E> optional) {
        Set<E> list = getSet(key);
        return list == null ? optional : list;
    }

    default <E> Set<E> getSet(K key, Class<E> elementType, Set<E> optional) {
        Set<E> list = getSet(key, elementType);
        return list == null ? optional : list;
    }
}
