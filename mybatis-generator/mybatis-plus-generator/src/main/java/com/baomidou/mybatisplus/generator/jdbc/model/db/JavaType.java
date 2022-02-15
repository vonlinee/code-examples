package com.baomidou.mybatisplus.generator.jdbc.model.db;

/**
 * every instance of Class<?> is singleton
 * @author someone
 */
public abstract class JavaType {
	protected Class<?> classType = Void.class;

	public final <T> boolean typeof(Class<T> clazz) {
		return this.classType == clazz;
	}

	public final boolean isString() {
		return this.classType == String.class;
	}

	public final boolean isNumber() {
		return this.classType == Number.class;
	}

	public final boolean isInteger() {
		return this.classType == Integer.class;
	}

	public final boolean isDouble() {
		return this.classType == Double.class;
	}

	public final boolean isFloat() {
		return this.classType == Float.class;
	}

	public final boolean isLong() {
		return this.classType == Long.class;
	}

	public final boolean isVoid() {
		return this.classType == Void.class;
	}
}
