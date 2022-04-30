package io.maker.base.lang;

public final class NamedValue extends Value {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	
	private static final String UNKNOWN_NAME = "null";
	
	private String name;
	
	protected <T> NamedValue(T value) {
		super(value);
	}
	
	public <T> NamedValue(String name, T value) {
		this(value);
		this.name = name == null ? UNKNOWN_NAME : name;
	}

	public String getName() {
		return this.name;
	}
}
