package io.devpl.sdk.internal;

/**
 * generic builder pattern
 * @param <T> the type that this Builder to build
 * @param <B> the type of Builder
 */
public interface Builder<T, B extends Builder<T, B>> {

    T build();

    B getThis();
}
