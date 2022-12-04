package io.devpl.sdk.util;

import java.io.Serializable;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.regex.Pattern;

/**
 * 用于存储数据，非线程安全，不用做对象属性，用法类似HashMap
 */
public interface DataObject extends Serializable, Cloneable {

    /**
     * 小驼峰命名
     */
    Pattern NAME_PATTERN = Pattern.compile("[a-zA-z_][a-zA-z0-9_]*");

    /**
     * 是否存在某个名称的数据项
     * @param key 数据项名称
     * @return 是否存在某个名称的数据项
     */
    boolean containsKey(String key);

    /**
     * 是否存在某个数据值
     * @param value 数据值
     * @return 是否存在某个数据值
     */
    boolean containsValue(Object value);

    /**
     * 设置name的值，如果name不存在，则抛出异常
     * @param name  数据项的名称
     * @param value 数据项的新值
     * @throws NoSuchElementException 如果name指示的元素不存在，抛出此异常
     */
    void setValue(String name, Object value) throws NoSuchElementException;

    /**
     * 放入一个新的数据项，若name指示的数据项已存在，直接覆盖
     * @param name  数据项的名称
     * @param value 数据项的新值
     * @return DataObject 提供链式调用API
     */
    DataObject put(String name, Object value);

    /**
     * 根据名称获取值
     * @param name 数据项名称
     * @param <V>  期望的值类型
     * @return 值，如果实际的类型和期望的值类型不兼容，会抛出ClassCastException
     */
    <V> V getValue(String name);

    /**
     * 获取和指定Class兼容的类型，如果没有则返回null
     * @param name      key
     * @param valueType 期望的值类型，必须相等才认为是同类型，不允许向上造型
     * @param <V>       期望的值类型
     * @return 期望的值，如果实际的类型和期望的值类型不同，会抛出ClassCastException
     */
    default <V> V getValue(String name, Class<V> valueType) {
        final Object val = getValue(name);
        if (val != null && valueType != null && valueType.isAssignableFrom(val.getClass())) {
            @SuppressWarnings("unchecked") V valFound = (V) val;
            return valFound;
        }
        return null;
    }

    /**
     * 根据名称获取值
     * @param name 数据项名称
     * @param <V>  期望的值类型
     * @return 期望的值，如果实际的类型和期望的值类型不兼容，会抛出ClassCastException
     */
    default <V> V getValue(String name, V defaultValue) {
        final Object val = getValue(name);
        if (val == null) {
            return defaultValue;
        }
        @SuppressWarnings("unchecked") V valFound = (V) val;
        return valFound;
    }

    /**
     * 根据名称获取值
     * @param name         数据项名称
     * @param valueType    期望的值类型Class
     * @param defaultValue 默认值
     * @param <V>          期望的值类型
     * @return 期望的值，如果实际的类型和期望的值类型valueType不同，返回默认值
     */
    default <V> V getValue(String name, Class<V> valueType, V defaultValue) {
        final Object val = getValue(name);
        if (val == null) {
            return defaultValue;
        } else if (valueType != null && valueType.isAssignableFrom(val.getClass())) {
            @SuppressWarnings("unchecked") V valFound = (V) val;
            return valFound;
        }
        return null;
    }

    /**
     * 判断两个DataObject是否相等，只需要比较DataObject所包含的数据，和DataObject本身无关系
     * @param obj 另一个DataObject
     * @return boolean
     */
    default boolean equals(DataObject obj) {
        if (obj == null) {
            return false;
        }
        return asMap().equals(obj.asMap());
    }

    /**
     * 返回一个DataItem迭代器
     * @return 迭代器
     */
    Iterator<DataItem> iterator();

    /**
     * 返回DataObject自身的hashCode，一般用不到此方法
     * @return hashcode
     */
    @Override
    int hashCode();

    /**
     * 如果要显示数据内容，需要通过asMap()方法，而不是通过此方法
     * 此方法应该对DataObject自身进行toString()
     * @return String
     */
    @Override
    String toString();

    /**
     * 将存放的数据转换成Map
     * @return 包含所有数据项的map，不能为空
     */
    Map<String, Object> asMap();

    /**
     * 复制数据到一个新的DataObject
     * @return DataObject
     */
    DataObject copy();
}
