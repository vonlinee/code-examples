package org.example.springboot.common;

public final class Box<T> implements Cloneable {
	
	private T something;
	private volatile boolean isEmpty;
	
	private Box(T something, boolean isEmpty) {
		this.something = something;
		this.isEmpty = isEmpty;
	}
	
	public static <T> Box<T> wrap(T something, T placeholder) throws IllegalArgumentException {
		Box<T> box = null;
		if (something == null) {
			if (placeholder == null) {
				throw new IllegalArgumentException("placeholder cannot be null");
			}
			box = new Box<T>(placeholder, true);
		}
		box = new Box<T>(something, false);
		return box;
	}
	
	public static <T> Box<T> wrap(T something) {
		Box<T> box = null;
		if (something == null) {
			box = new Box<T>(something, true);
		}
		box = new Box<T>(something, false);
		return box;
	}
	
	public final T unwrap() {
		if (isEmpty) {
			throw new NullPointerException("the box is empty");
		}
		return something;
	}
	
	public final boolean isEmpty() {
		return isEmpty;
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
	
	public boolean equals(Box<T> box) {
		return something.equals(box.something);
	}
}
