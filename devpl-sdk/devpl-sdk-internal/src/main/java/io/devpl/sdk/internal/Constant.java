package io.devpl.sdk.internal.utils.enums;

/**
 * A singleton which is safe to compare via the {@code ==} operator. Created and
 * managed by {@link ConstantPool}.
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