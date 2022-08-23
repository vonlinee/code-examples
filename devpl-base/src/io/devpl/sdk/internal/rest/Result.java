package io.devpl.sdk.internal.rest;

import java.io.Serializable;

public abstract class Result implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5649624870938615530L;

	protected Integer code;

	protected String message;

	protected String description;

	protected String stacktrace;
	
	protected Object data;

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getStacktrace() {
		return stacktrace;
	}

	public void setStacktrace(String stacktrace) {
		this.stacktrace = stacktrace;
	}
}
