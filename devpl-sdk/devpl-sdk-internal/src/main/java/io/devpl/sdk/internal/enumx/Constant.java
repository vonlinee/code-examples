package io.devpl.sdk.internal.enumx;

/**
 * 单例常量通过 == 运算符进行比较
 * A singleton which is safe to compare via the {@code ==} operator. Created and
 * managed by {@link ConstantPool}.
 * @param <T> Constant类型，常量的值类型
 */
public interface Constant<T extends Constant<T>> extends Comparable<T> {

	/**
	 * Returns the unique number assigned to this {@link Constant}.
	 */
	int id();

	/**
	 * Returns the name of this {@link Constant}.
	 */
	String name();
}