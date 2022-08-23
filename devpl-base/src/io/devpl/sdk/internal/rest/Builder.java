package io.devpl.sdk.internal.rest;

public interface Builder<T> {

	Builder<T> code(int code);

	Builder<T> message(String message);

	Builder<T> status(ResponseStatus<Integer> status);

	Builder<T> description(String description);

	Builder<T> stackTrace(String description);

	Builder<T> data(T data);

	Builder<T> ext(String key, Object value);

	Result build();
}