package org.example.springboot.rest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	
	public void addTestData() {
		if (data == null) {
			data = new ArrayList<>();
		}
		if (data.isEmpty()) {
			for (int i = 0; i < 2; i++) {
				Map<String, Object> map = new HashMap<>();
				for (int j = 0; j < 5; j++) {
					map.put("key" + i + "" + j, "value" + i + "" + j);
				}
				data.add((T) map);
			}
		}
	}
}