package io.maker.codegen.mbp.fill;

import java.io.Serializable;

public class Attribute implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private int order;
	private String name;
	private String value;

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "Attribute [order=" + order + ", name=" + name + ", value=" + value + "]";
	}
}
