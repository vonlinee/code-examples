package io.maker.base.lang;

public final class NamedValue extends Value {

	private String valueName;
	
	private static final String UNKNOWN_NAME = "unknown";
	
	private NamedValue(String name, Object value) {
		super(value);
		this.valueName = value == null ? "null" : name == null ? UNKNOWN_NAME : name;
	}
	
	public String getName() {
		return valueName;
	}
	
	public static <T> NamedValue of(String name, T value) {
		return new NamedValue(name, value);
	}
}
