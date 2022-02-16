package io.maker.base.rest;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * 单条返回结果
 * 
 * @author line
 *
 * @param <T>
 */
public final class EntityResult<T> extends Result<T> implements Serializable {

	private static final long serialVersionUID = 4134449411254581242L;

	private String code;
	private String message;

	public EntityResult() {
		super();
		if (this.description == null) {
			this.description = new ResultDescription("", "", null) {};
		}
	}
	
	public EntityResult(String code, String message) {
		super();
		if (this.description == null) {
			this.description = new ResultDescription(code, message, null) {};
		}
	}
	
	/**
	 * 
	 * @param description Must Not Null
	 */
	public EntityResult(ResultDescription description) {
		super();
		this.code = description.code;
		this.message = description.message;
	}

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

	public static <T> Builder<T> builder() {
		return new Builder<>();
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
		String errorMessage;
		T data;

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

		public Builder<T> errorMessage(String errorMsg) {
			this.errorMessage = errorMsg;
			return this;
		}

		public Builder<T> data(T data) {
			this.data = data;
			return this;
		}

		@SuppressWarnings({ "unchecked", "hiding" })
		public <T> EntityResult<T> build() {
			EntityResult<T> result = new EntityResult<>();
			result.setCode(this.code);
			result.setMessage(this.message);
			result.setErrorMsg(this.errorMessage);
			result.setData((T) data);
			result.setTimestamp(Timestamp.valueOf(LocalDateTime.now()).toString());
			return result;
		}
	}
}
