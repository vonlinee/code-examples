package io.maker.base.lang;

/**
 * 类型转换器
 * @param <K>
 * @param <V>
 */
public interface TypeConverter<K, V> {
    V convert(K srcType);
}
