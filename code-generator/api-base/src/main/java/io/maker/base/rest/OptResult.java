package io.maker.base.rest;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * the result of operation
 * @author vonline
 *
 * @param <T>
 */
public class OptResult<T> extends Result<T> implements Serializable {

	private static final long serialVersionUID = 1L;

	private String code;
	private String message;
	private final Map<String, String> extension = new HashMap<>(); //扩展信息
	
	public void setCode(String code) {
		this.code = this.description.code = code;
	}

	public void setMessage(String message) {
		this.message = this.description.message = message;
	}

	public String getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public String getErrorMessage() {
		return this.errorMsg;
	}

	public void setData(T data) {
		this.data = data;
	}

	public T getData() {
		return this.data;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getTimestamp() {
		return this.errorMsg;
	}

	
	/**
	 * Builder Pattern
	 * 
	 * @author line
	 * @param <T>
	 */
	public static class Builder<T> {
		String code;
		String message;
		String timestamp;

		public Builder<T> description(ResultDescription description) {
			this.code = description.code;
			this.message = description.message;
			return this;
		}

		public Builder<T> description(String code, String message) {
			this.code = code;
			this.message = message;
			return this;
		}

		@SuppressWarnings({ "hiding" })
		public <T> OptResult<T> build() {
			OptResult<T> result = new OptResult<T>();
			result.setCode(this.code);
			result.setMessage(this.message);
			result.setTimestamp(Timestamp.valueOf(LocalDateTime.now()).toString());
			return result;
		}
	}
}
