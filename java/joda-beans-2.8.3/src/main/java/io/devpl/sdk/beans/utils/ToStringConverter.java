package io.devpl.sdk.beans.utils;

/**
 * Interface defining conversion to a {@code String}.
 * <p>
 * ToStringConverter is an interface and must be implemented with care.
 * Implementations must be immutable and thread-safe.
 * 
 * @param <T>  the type of the converter
 */
public interface ToStringConverter<T> {

    /**
     * Converts the specified object to a {@code String}.
     * @param object  the object to convert, not null
     * @return the converted string, may be null but generally not
     */
    String convertToString(T object);
}