package coed.example.springboot.web;

import java.io.Serializable;

public class Param<T> implements Serializable {

	private static final long serialVersionUID = -1362976174136237346L;

	private String name;
	private T value;
	
	private transient Class<T> valueType;

	public Param(String name, T value) {
		super();
		this.name = name;
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public T getValue() {
		return value;
	}

	public void setValue(T value) {
		this.value = value;
	}

}
