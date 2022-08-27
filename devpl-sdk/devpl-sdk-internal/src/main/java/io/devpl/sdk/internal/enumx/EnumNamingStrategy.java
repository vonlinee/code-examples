package io.devpl.sdk.internal.enumx;

import io.devpl.sdk.internal.NamingStrategy;

/**
 * 枚举命名策略
 * @param <T>
 */
@FunctionalInterface
public interface EnumNamingStrategy<T extends TypedEnum<T>> extends NamingStrategy<T> {

}
