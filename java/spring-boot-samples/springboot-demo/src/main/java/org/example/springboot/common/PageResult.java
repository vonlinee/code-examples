package org.example.springboot.common;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public final class PageResult<T> extends Result<T> implements Serializable {

	private static final long serialVersionUID = 4134449411254581242L;

	private String code;
	private String message;
	private List<T> rows;

	public PageResult() {
		super();
		if (this.description == null) {
			this.description = new ResultDescription("", "", null) {
			};
		}
	}

	public PageResult(String code, String message) {
		super();
		if (this.description == null) {
			this.description = new ResultDescription(code, message, null) {
			};
		}
	}

	/**
	 * 
	 * @param description Must not be null
	 */
	public PageResult(ResultDescription description) {
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

	public void setRows(List<T> rows) {
		if (rows == null) {
			this.rows = new ArrayList<>(0);
		}
		this.rows = rows;
	}

	public List<T> getRows() {
		return rows;
	}
	
	public T getRow(int rowCount) {
		return rows.get(rowCount);
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
		List<T> rows;

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

		public Builder<T> data(List<T> rows) {
			this.rows = rows;
			return this;
		}

		@SuppressWarnings({ "unchecked", "hiding" })
		public <T> PageResult<T> build() {
			PageResult<T> result = new PageResult<>();
			result.setCode(this.code);
			result.setMessage(this.message);
			result.setErrorMsg(this.errorMessage);
			result.setRows((List<T>) this.rows);
			result.setTimestamp(Timestamp.valueOf(LocalDateTime.now()).toString());
			return result;
		}
	}
}