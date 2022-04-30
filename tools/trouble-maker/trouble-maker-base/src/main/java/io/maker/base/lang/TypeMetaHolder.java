package io.maker.base.lang;

import io.maker.base.annotation.Nullable;

public class TypeMetaHolder {

	private final Class<?> clazz;
	
	public TypeMetaHolder(Object value) {
		if (value != null) {
			this.clazz = value.getClass();
		} else {
			this.clazz = null;
		}
	}

	/**
	 * 
	 * @param <V>
	 * @param requiredType cannot be null
	 * @param targetValue maybe null
	 * @return
	 */
	@Nullable
	@SuppressWarnings("unchecked")
	public final <V> V tryCastAfterCheck(Class<V> requiredType, Object targetValue) {
		if (targetValue == null) { // null type can be casted to any type
			return (V) targetValue;
		}
		if (clazz != null && clazz.isAssignableFrom(requiredType)) {
			return (V) targetValue;
		}
		// handle compatiable type like int/double/long/byte
		throw new ClassCastException(
				String.format("origin type[%s] cannot be casted to required type[%s] with the value[%s]", clazz,
						requiredType, targetValue));
	}
	
	@Nullable
	@SuppressWarnings("unchecked")
	public final <V> V tryCast(Class<V> requiredType, Object targetValue) {
		return (V) targetValue;
	}
	
	@Nullable
	@SuppressWarnings("unchecked")
	public final <V> V tryCast(Object targetValue) {
		return (V) targetValue;
	}
}
