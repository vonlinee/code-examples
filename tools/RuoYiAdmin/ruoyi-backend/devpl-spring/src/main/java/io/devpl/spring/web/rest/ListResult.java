package io.devpl.spring.web.rest;

import java.util.ArrayList;
import java.util.List;

public class ListResult<T> extends AbstractResult<List<T>> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3138900734117740286L;

	public ListResult() {
		this.timestamp = System.currentTimeMillis();
	}
	
	public ListResult(int code, String message) {
		this();
		this.code = code;
		this.msg = message;
		if (this.data == null) {
			this.data = new ArrayList<>(0);
		}
	}
	
	public ListResult(int code, String message, List<T> data) {
		this();
		this.code = code;
		this.msg = message;
		this.data = data;
	}
	
	public void setData(List<T> data) {
		this.data = data;
	}
	
	public List<T> getData() {
		return data;
	}
	
	@Override
	protected String serialize() {
		return data.toString();
	}
}
