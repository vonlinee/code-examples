package utils;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public final class BeanMap implements Map<String, Object> {

	LinkedHashMap<String, Object> map;
	
	@Override
	public int size() {
		return 0;
	}

	@Override
	public boolean isEmpty() {
		return false;
	}

	@Override
	public boolean containsKey(Object key) {
		return false;
	}

	@Override
	public boolean containsValue(Object value) {
		return false;
	}

	@Override
	public Object get(Object key) {
		return null;
	}

	@Override
	public Object put(String key, Object value) {
		return null;
	}

	@Override
	public Object remove(Object key) {
		return null;
	}

	@Override
	public void putAll(Map<? extends String, ? extends Object> m) {
		
	}

	@Override
	public void clear() {
		
	}

	@Override
	public Set<String> keySet() {
		return null;
	}

	@Override
	public Collection<Object> values() {
		return null;
	}

	@Override
	public Set<Entry<String, Object>> entrySet() {
		return null;
	}
	
	class FieldEntry implements Map.Entry<String, Object> {

		@Override
		public String getKey() {
			return null;
		}

		@Override
		public Object getValue() {
			return null;
		}

		@Override
		public Object setValue(Object value) {
			return null;
		}
	}
}
