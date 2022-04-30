package io.maker.base.collection;

import java.util.HashMap;
import java.util.Properties;

/**
 * 存储配置项, Properties都是同步方法
 */
public final class ConfigMap extends HashMap<String, Object> {

	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	public <T> T getField(String configName) {
		return (T) get(configName);
	}
	
	/**
	 * 
	 * @param properties
	 */
	public void putAll(Properties properties) {
		// java.util.HashMap.KeySet<K, V>  Properties的keySet是同步的
		boolean result = properties.keySet().addAll(this.keySet());
		
	}
}
