package io.maker.base.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

/**
 * Map工具类
 */
public final class Maps {

	private Maps() {
	}

	/**
	 * 打印Map
	 * 
	 * @param map
	 */
	public static String println(Map<?, ?> map) {
		System.out.println("共" + map.size() + "个元素:");
		StringBuilder sb = new StringBuilder("{\n");
		for (Object key : map.keySet()) {
			sb.append("\t").append("\"").append(key).append("\":\"").append(map.get(key)).append("\",\n");
		}
		return sb.substring(0, sb.length()) + "\n";
	}

	public static <K, V> Map<K, V> doFilterKey(final Map<K, V> map, Predicate<K> rule) {
		Map<K, V> newMap = new HashMap<>();
		map.keySet().stream().filter(rule).forEach(k -> {
			newMap.put(k, map.get(k));
		});
		return newMap;
	}

	public static <K, V> Map<K, V> doFilterValue(Map<K, V> map, Predicate<V> rule) {
		Map<K, V> newMap = new HashMap<>();
		map.forEach((k, v) -> {
			if (!rule.test(v)) {
				newMap.put(k, v);
			}
		});
		return newMap;
	}

	@SuppressWarnings("unchecked")
	public static <K> K[] keys(Map<K, ?> map) {
		return (K[]) map.keySet().toArray();
	}

	@SuppressWarnings("unchecked")
	public static <K> K[] values(Map<K, ?> map) {
		return (K[]) map.values().toArray();
	}

	/**
	 * 拷贝Map元素，不影响原来的Map
	 * 
	 * @param <K>
	 * @param <V>
	 */
	public static class MapCopier<K, V> {

		private List<K> fromKeys;
		private List<K> toKeys;

		public MapCopier() {
			this.fromKeys = new ArrayList<>();
			this.toKeys = new ArrayList<>();
		}

		public MapCopier<K, V> addKey(K fromKey, K toKey) {
			fromKeys.add(fromKey);
			toKeys.add(toKey);
			return MapCopier.this;
		}

		public <T extends Map<K, V>> Map<K, V> copy(final T fromMap) {
			Map<K, V> newMap = new HashMap<>();
			for (int i = 0; i < fromKeys.size(); i++) {
				newMap.put(toKeys.get(i), fromMap.get(fromKeys.get(i)));
			}
			return newMap;
		}
	}

	public static <K, V> MapCopier<K, V> copier() {
		return new MapCopier<>();
	}

	/**
	 * 获取值，如果发生类型转换异常，则提供默认值
	 * 
	 * @param map
	 * @param key
	 * @param optionalValue
	 * @return
	 */
	public static String getString(Map<String, Object> map, String key, String optionalValue) {
		Object value = map.get(key);
		try {
			return (String) value;
		} catch (ClassCastException exception) {
			if (value instanceof Number) {
				return String.valueOf(value);
			}
		}
		return optionalValue;
	}

	public static String getString(Map<String, Object> map, String key) {
		return getString(map, key, "");
	}

	public static <K, V extends Number> int getInt(Map<K, V> map, K key, int optionalValue) {
		if (map.containsKey(key)) {
			V number = map.get(key);
			if (number == null) {
				return optionalValue;
			}
			return number.intValue();
		}
		return optionalValue;
	}

	@SuppressWarnings("unchecked")
	public static <K, V, T> T getValue(Map<K, V> map, K key, V optionalValue) {
		V v = map.get(key);
		if (v == null) {
			return (T) optionalValue;
		}
		return (T) v;
	}

	public static <K, V, T> T getValue(Map<K, V> map, K key) {
		return getValue(map, key, null);
	}

	public static <K, V extends Number> int getInt(Map<K, V> map, K key) {
		if (map.containsKey(key)) {
			V number = map.get(key);
			if (number == null) {
				throw new NullPointerException(key + " exist in the map but is null");
			}
			return number.intValue();
		}
		throw new NullPointerException(key + " does not exist in the map");
	}

	public static <K, V> Map<K, V> putAll(Map<K, V> map, List<K> keys, List<V> values) {
		int size = keys.size();
		if (size != values.size()) {
			throw new IllegalArgumentException("the size of key must be equals to the size of values in map!");
		}
		for (int i = 0; i < size; i++) {
			map.put(keys.get(i), values.get(i));
		}
		return map;
	}

	public static <K, V> Map<K, V> putAll(Map<K, V> map, List<K> keys, List<V> values, boolean kvSizeEquals) {
		if (kvSizeEquals) {
			putAll(map, keys, values);
		}
		return map;
	}

	/**
	 * @param map
	 * @param m
	 * @return Map<K, V>
	 */
	public static <K, V> Map<K, V> putAll(Map<K, V> map, Map<? extends K, ? extends V> m) {
		map.putAll(m);
		return map;
	}

	public static <K, V> Map<K, V> setValues(Map<K, V> map, Predicate<K> rule, V value) {
		for (Map.Entry<K, V> entry : map.entrySet()) {
			if (rule.test(entry.getKey())) {
				entry.setValue(value);
			}
		}
		return map;
	}

	public static <K, V> Map<K, V> setValues(Map<K, V> map, V value) {
		for (Map.Entry<K, V> entry : map.entrySet()) {
			entry.setValue(value);
		}
		return map;
	}

	public static <K, V> Map<K, V> setNullValues(Map<K, V> map) {
		for (Map.Entry<K, V> entry : map.entrySet()) {
			entry.setValue(null);
		}
		return map;
	}

	/**
	 * 由于是static方法，因此需要Class辅助进行类型推断，否则运行时都是Object类型
	 * 
	 * @param keyType
	 * @param valueType
	 * @return MapBuilder<K, V>
	 */
	public static <K, V> MapBuilder<K, V> builder(Class<K> keyType, Class<V> valueType) {
		return new MapBuilder<K, V>();
	}

	public static <V> MapBuilder<String, V> stringKeybuilder(Class<V> valueType) {
		return new MapBuilder<String, V>();
	}

	public static <V> MapBuilder<String, String> stringMapBuilder() {
		return new MapBuilder<String, String>();
	}

	public static MapBuilder<String, Object> builder() {
		return new MapBuilder<>();
	}

	/**
	 * @param <K>
	 * @param <V>
	 */
	public static class MapBuilder<K, V> {

		static final float DEFAULT_LOAD_FACTOR = 0.75f;
		static final int DEFAULT_INTIAL_CAPACITY = 16;

		private List<K> keys;
		private List<V> values;
		private int initialCapacity;
		private float loadFactor;
		private Map<K, V> existedMap;
		private boolean useExistedMap = false;

		private Class<? extends Map<K, V>> mapType;

		public MapBuilder() {
			this(DEFAULT_INTIAL_CAPACITY);
		}

		public MapBuilder(int intialCapacity) {
			this(intialCapacity, DEFAULT_LOAD_FACTOR);
		}

		public MapBuilder(int initialCapacity, float loadFactor) {
			this(initialCapacity, loadFactor, HashMap.class);
		}

		@SuppressWarnings("unchecked")
		public MapBuilder(int initialCapacity, float loadFactor,
				@SuppressWarnings("rawtypes") Class<? extends Map> mapType) {
			this.mapType = (Class<? extends Map<K, V>>) mapType;
			this.initialCapacity = initialCapacity;
			if (initialCapacity <= 0) {
				throw new IllegalArgumentException("initialCapacity cannot be less than zero!");
			}
			this.loadFactor = loadFactor;
			this.keys = new ArrayList<>(initialCapacity);
			this.values = new ArrayList<>(initialCapacity);
		}

		public MapBuilder<K, V> source(Map<K, V> map) {
			this.existedMap = map;
			this.useExistedMap = true;
			return this;
		}

		public MapBuilder<K, V> initialCapacity(int initialCapacity) {
			this.initialCapacity = initialCapacity;
			return this;
		}

		public MapBuilder<K, V> loadFactor(float loadFactor) {
			this.loadFactor = loadFactor;
			return this;
		}

		public MapBuilder<K, V> put(K key, V value) {
			if (useExistedMap) {
				existedMap.put(key, value);
			} else {
				if (key == null) {
					keys.add(0, key);
					values.add(0, value);
				} else {
					keys.add(key);
					values.add(value);
				}
			}
			return this;
		}

		public Map<K, V> build() {
			Map<K, V> map;
			if (existedMap != null) {
				map = Maps.putAll(existedMap, keys, values);
			} else {
				if (mapType == null) {
					map = new HashMap<>(initialCapacity, loadFactor);
				} else {
					try {
						map = mapType.newInstance(); // 性能评估
					} catch (InstantiationException | IllegalAccessException e) {
						map = new HashMap<>(initialCapacity, loadFactor);
					}
				}
				putAll(map, keys, values);
			}
			return map;
		}
	}
}
