package io.maker.codegen.core.lang.meta;

import lombok.Data;

import java.io.Serializable;

public class FieldDefinition implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4794635022478483091L;

	private String name;
	private int modifiers;
	private boolean isPrimitive;
	private String belongToClass;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getModifiers() {
		return modifiers;
	}

	public void setModifiers(int modifiers) {
		this.modifiers = modifiers;
	}

	public boolean isPrimitive() {
		return isPrimitive;
	}

	public void setPrimitive(boolean isPrimitive) {
		this.isPrimitive = isPrimitive;
	}

	public String getBelongToClass() {
		return belongToClass;
	}

	public void setBelongToClass(String belongToClass) {
		this.belongToClass = belongToClass;
	}
}
