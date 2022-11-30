package io.devpl.sdk.beans.utils;

/**
 * Interface defining conversion to and from a {@code String}.
 * <p>
 * StringConverter is an interface and must be implemented with care.
 * Implementations must be immutable and thread-safe.
 * @param <T> the type of the converter
 */
public interface StringConverter<T> extends ToStringConverter<T>, FromStringConverter<T> {

}