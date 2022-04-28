package io.maker.base.collection;

import java.util.HashMap;

public class ValueMap<K> extends HashMap<K, Object> {

	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	public <T> T getValue(Object key) {
		return (T) get(key);
	}
}
