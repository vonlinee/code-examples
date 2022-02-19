package io.maker.base.lang;

/**
 * 包装Class<?>实例，基于Class<?>对象是单例的提供类型判断 子类实现需要对typeClass赋值
 * @author vonline
 */
public abstract class JavaType {
	protected Class<?> typeClass;

	public final <T> boolean typeof(Class<T> clazz) {
		return this.typeClass == clazz;
	}

	public final String type() {
		return typeClass == null ? "null" : typeClass.getName();
	}

	public final boolean isString() {
		return this.typeClass == String.class;
	}

	public final boolean isByte() {
		return this.typeClass == Byte.class;
	}

	public final boolean isNumber() {
		return this.typeClass == Number.class;
	}

	public final boolean isInteger() {
		return this.typeClass == Integer.class;
	}

	public final boolean isDouble() {
		return this.typeClass == Double.class;
	}

	public final boolean isFloat() {
		return this.typeClass == Float.class;
	}

	public final boolean isLong() {
		return this.typeClass == Long.class;
	}

	public final boolean isVoid() {
		return this.typeClass == Void.class;
	}

	/**
	 * 无法判断是否是引用类型
	 * @return
	 */
	public final boolean isPrimitive() {
		return this.typeClass == Byte.class || this.typeClass == Number.class || this.typeClass == Integer.class
				|| this.typeClass == Double.class || this.typeClass == Float.class || this.typeClass == Long.class
				|| this.typeClass == Void.class;
	}
}
