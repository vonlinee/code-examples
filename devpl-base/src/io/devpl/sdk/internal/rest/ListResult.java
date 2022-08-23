package io.devpl.sdk.internal.rest;

import java.util.List;

public class ListResult<T> extends Result {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	public List<T> getData() {
		return (List<T>) data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}
}
