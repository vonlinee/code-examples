package io.maker.base.beans;

public interface DynamicBean {

	/**
	 * 为某个字段赋值
	 * 
	 * @param <V>
	 * @param fieldName
	 * @param value
	 * @param addIfNotExists 当字段不存在时是否新增
	 * @return
	 * @throws NoSuchFieldException
	 */
	<V> V set(String fieldName, V value, boolean addIfNotExists) throws NoSuchFieldException;

	/**
	 * 获取字段值
	 * 
	 * @param <V>
	 * @param fieldName
	 * @return
	 * @throws NoSuchFieldException
	 */
	<V> V get(String fieldName) throws NoSuchFieldException;

	/**
	 * 移除某个字段
	 * 
	 * @param fieldName
	 */
	void remove(String fieldName);

	/**
	 * 是否包含某个字段
	 * 
	 * @param fieldName
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
