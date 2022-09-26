package io.devpl.sdk;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * 基本类型的getter接口<br>
 * 提供一个统一的接口定义返回不同类型的值（基本类型）<br>
 *
 * @param <K> key类型
 */
public interface TypedGetter<K> {

    /*-------------------------- 基本类型 start -------------------------------*/

    /**
     * 获取Object属性值
     *
     * @param key 属性名
     * @return 属性值
     */
    Object getObject(K key);

    /**
     * 获取字符串型属性值
     *
     * @param key 属性名
     * @return 属性值
     */
    String getString(K key);

    /**
     * 获取int型属性值
     *
     * @param key 属性名
     * @return 属性值
     */
    Integer getInteger(K key);

    /**
     * 获取short型属性值
     *
     * @param key 属性名
     * @return 属性值
     */
    Short getShort(K key);

    /**
     * 获取boolean型属性值
     *
     * @param key 属性名
     * @return 属性值
     */
    Boolean getBoolean(K key);

    /**
     * 获取long型属性值
     *
     * @param key 属性名
     * @return 属性值
     */
    Long getLong(K key);

    /**
     * 获取char型属性值
     *
     * @param key 属性名
     * @return 属性值
     */
    Character getCharacter(K key);

    /**
     * 获取float型属性值<br>
     *
     * @param key 属性名
     * @return 属性值
     */
    Float getFloat(K key);

    /**
     * 获取double型属性值
     *
     * @param key 属性名
     * @return 属性值
     */
    Double getDouble(K key);

    /**
     * 获取byte型属性值
     *
     * @param key 属性名
     * @return 属性值
     */
    Byte getByte(K key);

    /**
     * 获取BigDecimal型属性值
     *
     * @param key 属性名
     * @return 属性值
     */
    BigDecimal getBigDecimal(K key);

    /**
     * 获取Number型属性值
     *
     * @param key
     * @return
     */
    Number getNumber(K key);

    /**
     * 获取BigInteger型属性值
     *
     * @param key 属性名
     * @return 属性值
     */
    BigInteger getBigInteger(K key);

    /**
     * 获得Enum类型的值
     *
     * @param <E>   枚举类型
     * @param clazz Enum的Class
     * @param key   KEY
     * @return Enum类型的值，无则返回Null
     */
    <E extends Enum<E>> E getEnum(Class<E> clazz, K key);

    /*-------------------------- 基本类型 end -------------------------------*/

    /**
     * 获取Date类型值
     *
     * @param key 属性名
     * @return Date类型属性值
     */
    Date getDate(K key);

    /**
     * 获取LocalDateTime类型值
     *
     * @param key
     * @return
     */
    LocalDateTime getLocalDateTime(K key);

    /**
     * 获取自定义类型
     *
     * @param key
     * @param type
     * @param <V>
     * @return
     */
    <V> V get(K key, Class<V> type);
}
