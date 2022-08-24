package io.devpl.sdk.internal.rest;

/**
 * Builder Pattern
 * @param <R>
 */
public interface Builder<R extends ResultTemplate> {

    Builder<R> code(int code);

    Builder<R> message(String message);

    Builder<R> description(String description);

    Builder<R> data(Object data);

    R build();
}
