package io.maker.base.utils;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MapConfigRepository implements ConfigRepository {

	private final Map<String, Object> configurations = new ConcurrentHashMap<>();
	
	@Override
	public String name() {
		return this.toString();
	}

	@Override
	public boolean contains(String configName) {
		return configurations.containsKey(configName);
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T> T set(String configName, T configValue) {
		return (T) configurations.put(configName, configValue);
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T> T update(String configName, T configValue) {
		return (T) configurations.replace(configName, configValue);
	}

	@Override
	public boolean delete(String configName) {
		if (configurations.containsKey(configName)) {
			configurations.remove(configName);
			return true;
		}
		return false;
	}

	@Override
	public String getString(String configName) {
		return (String) configurations.get(configName);
	}

	@Override
	@SuppressWarnings("unchecked")
	public <K, V> Map<K, V> getMap(String configName) {
		return (Map<K, V>) configurations.get(configName);
	}

	@Override
	public <E> List<E> getList(String configName) {
		return null;
	}

	@Override
	public Object get(String configName) {
		return configurations.get(configName);
	}
}
