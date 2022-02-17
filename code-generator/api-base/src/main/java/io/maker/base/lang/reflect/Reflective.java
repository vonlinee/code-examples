package io.maker.base.lang.reflect;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public final class Reflective {

	/**
	 * @param <T>
	 * @param type
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * @throws SecurityException
	 */
	@SuppressWarnings("unchecked")
	public static <T extends Object> T newInstance(Class<T> type) throws InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException, SecurityException {
		Constructor<?>[] constructors = type.getDeclaredConstructors();
		if (constructors.length >= 1) {
			return (T) constructors[0].newInstance(new Object[] {});
		}
		return null;
	}
}
