package io.devpl.sdk.internal;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * 提供默认值，不对默认值进行检查，由调用方保证其是否符合要求
 * @param <K>
 */
public interface PlaceholderTypedGetter<K> extends TypedGetter<K> {

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
}
