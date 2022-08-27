package io.devpl.sdk.internal;

/**
 * 可以作为业务类规范和数据库表规范的桥梁
 * @param <T>
 */
@FunctionalInterface
public interface NamingStrategy<T> {
    String name(T value);
}
