package io.devpl.sdk.beans.utils;

/**
 * Interface defining conversion from a {@code String}.
 * <p>
 * FromStringConverter is an interface and must be implemented with care.
 * Implementations must be immutable and thread-safe.
 * @param <T> the type of the converter
 */
public interface FromStringConverter<T> {

    /**
     * Converts the specified object from a {@code String}.
     * @param cls the class to convert to, not null
     * @param str the string to convert, not null
     * @return the converted object, may be null but generally not
     */
    T convertFromString(Class<? extends T> cls, String str);

}