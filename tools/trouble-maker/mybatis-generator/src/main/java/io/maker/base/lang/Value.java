package io.maker.base.lang;

import java.io.Serializable;

public abstract class Value implements Serializable {

	private static final long serialVersionUID = 1L;
	
	// nullable
	protected Object value;
	protected transient Class<?> typeClass;
	
	protected Value(Object value) {
		super();
		set(value);
	}

	public void set(Object value) {
		this.value = value;
		this.typeClass = value != null ? value.getClass() : null;
	}
	
	@SuppressWarnings("unchecked")
	public final <T> T get() {
		return (T) value;
	}
	
	public static Value wrap(Object value) {
		return new SimpleValue(value);
	}
	
	public static Value wrapFinal(Object value) {
		return new FinalValue(value);
	}
	
	public static Value empty(Object value) {
		return new NullValue(value);
	}
	
	public static Value empty(Object value, Class<?> clazz) {
		Value value1 = new NullValue(value);
		value1.typeClass = clazz;
		return value1;
	}
	
	public final boolean isNull() {
		return value == null;
	}
	
	public final Class<?> getTypeClass() {
		return typeClass;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Value) {
			Value val = (Value) obj;
			if (this.value == null) {
				return val.value == null && this.typeClass == val.typeClass;
			}
			return this.value.equals(val.value) && this.typeClass == val.typeClass;
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		if (this.value == null) {
			return 0;
		}
		return this.value.hashCode();
	}

	/**
	 * 默认实现
	 */
	private static class FinalValue extends Value {

		private static final long serialVersionUID = 1L;

		protected FinalValue(Object value) {
			super(value);
		}

		@Override
		public void set(Object value) {
			throw new UnsupportedOperationException("update the value is not supported!");
		}
	}
	
	private static class SimpleValue extends Value {

		private static final long serialVersionUID = 1L;

		protected SimpleValue(Object value) {
			super(value);
		}
	}
	
	private static class NullValue extends Value {

		private static final long serialVersionUID = 1L;

		protected NullValue(Object value) {
			super(value);
		}
	}
}
