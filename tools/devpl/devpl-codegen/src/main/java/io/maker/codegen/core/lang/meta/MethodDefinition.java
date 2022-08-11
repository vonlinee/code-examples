package io.maker.codegen.core.lang.meta;

import lombok.Data;

import java.io.Serializable;
import java.util.Map;

public class MethodDefinition implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1996162482198245048L;

	private int modifiers;
	private String methodName;
	private Map<String, String> declaredAnnotations;

	public void addDeclaredAnnotation(String name, String annotationName) {
		declaredAnnotations.put(name, annotationName);
	}

	public int getModifiers() {
		return modifiers;
	}

	public void setModifiers(int modifiers) {
		this.modifiers = modifiers;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public Map<String, String> getDeclaredAnnotations() {
		return declaredAnnotations;
	}

	public void setDeclaredAnnotations(Map<String, String> declaredAnnotations) {
		this.declaredAnnotations = declaredAnnotations;
	}
}
