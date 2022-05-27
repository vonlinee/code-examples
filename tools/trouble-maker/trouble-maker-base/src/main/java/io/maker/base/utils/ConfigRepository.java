package io.maker.base.utils;

import java.util.List;
import java.util.Map;

/**
 * 存储一系列配置信息
 */
public interface ConfigRepository {

	/**
	 * 此配置仓库的名称
	 * @return
	 */
	String name();
	
	boolean contains(String configName);
	
	<T> T set(String configName, T configValue);
	
	<T> T update(String configName, T configValue);
	
	boolean delete(String configName);
	
	/**
	 * 获取String类型的配置
	 * @param configName
	 * @return
	 */
	String getString(String configName);
	
	<K, V> Map<K, V> getMap(String configName);
	
	<E> List<E> getList(String configName);
	
	Object get(String configName);
}
