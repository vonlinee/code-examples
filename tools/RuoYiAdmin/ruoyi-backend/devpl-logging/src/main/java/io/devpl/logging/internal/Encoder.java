package io.devpl.logging.internal;

/**
 * 将对象转为二进制数据
 * Objects implementing the {@code Encoder} interface know how to convert an object to some binary representation and
 * write the result to a {@code ByteBuffer}, ideally without creating temporary objects.
 * @param <T> the type of objects that the Encoder can encode
 * @param <R> 目标对象
 * @since 2.6
 */
@FunctionalInterface
public interface Encoder<T, R> {

    /**
     * Encodes the specified source object to some binary representation and writes the result to the specified
     * destination.
     * @param source the object to encode.
     */
    R encode(T source);
}