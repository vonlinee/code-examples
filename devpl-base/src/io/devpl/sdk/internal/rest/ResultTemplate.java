package io.devpl.sdk.internal.rest;

import java.util.List;

public class ResultTemplate<T> implements Builder<T> {

	private final Result result;
	
	private ResultTemplate(Result result) {
		this.result = result;
	}

	@Override
	public Builder<T> code(int code) {
		result.setCode(code);
		return this;
	}

	@Override
	public Builder<T> message(String message) {
		result.setMessage(message);
		return this;
	}

	@Override
	public Builder<T> status(ResponseStatus<Integer> status) {
		result.setCode(status.getCode());
		result.setMessage(status.getMessage());
		return this;
	}

	@Override
	public Builder<T> description(String description) {
		result.setDescription(description);
		return this;
	}

	@Override
	public Builder<T> stackTrace(String stackTrace) {
		result.setStacktrace(stackTrace);
		return this;
	}

	@Override
	@SuppressWarnings("unchecked")
	public Builder<T> data(T data) {
		if (result instanceof ListResult && data instanceof List) {
			ListResult<T> listResult = (ListResult<T>) result;
			listResult.setData((List<T>) data);
		}
		return this;
	}

	@Override
	public Builder<T> ext(String key, Object value) {
		return this;
	}

	@Override
	public Result build() {
		return result;
	}
	
	public static <T> ResultTemplate<List<T>> list() {
		return new ResultTemplate<>(new ListResult<>());
	}
	
	/**
	 * 传递泛型信息
	 * @param <T>
	 * @param type
	 * @return
	 */
	public static <T> ResultTemplate<List<T>> list(Class<T> type) {
		return new ResultTemplate<>(new ListResult<>());
	}
}
