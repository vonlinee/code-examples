package io.maker.base.collection;

import java.util.HashMap;

public final class BeanMap extends HashMap<String, Object> {

	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	public <T> T getField(String name) {
		return (T) get(name);
	}
}
