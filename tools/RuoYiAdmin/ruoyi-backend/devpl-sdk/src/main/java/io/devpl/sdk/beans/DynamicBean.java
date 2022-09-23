package io.devpl.sdk.beans;

public interface DynamicBean {

    /**
     * 为某个字段赋值
     *
     * @param <V>            字段值类型
     * @param fieldName      字段名
     * @param value          字段值
     * @param addIfNotExists 当字段不存在时是否新增
     * @return
     * @throws NoSuchFieldException
     */
    <V> V set(String fieldName, V value, boolean addIfNotExists) throws NoSuchFieldException;

    /**
     * 获取字段值
     *
     * @param <V>       字段值类型
     * @param fieldName 字段名
     * @return
     * @throws NoSuchFieldException
     */
    <V> V get(String fieldName) throws NoSuchFieldException;

    /**
     * 移除某个字段
     *
     * @param fieldName 字段名
     */
    void remove(String fieldName);

    /**
     * 是否包含某个字段
     *
     * @param fieldName 字段名
     * @return
     */
    boolean contains(String fieldName);

    /**
     * 字段的数量
     *
     * @return
     */
    int count();
}
