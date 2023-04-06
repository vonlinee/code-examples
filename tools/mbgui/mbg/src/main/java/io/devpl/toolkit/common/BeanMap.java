package io.devpl.toolkit.common;

import java.lang.reflect.Field;
import java.util.function.Consumer;
import java.util.function.Function;

public interface BeanMap {

    /**
     * 设置字段值
     *
     * @param key   字段名
     * @param value 字段值
     * @param <V>   字段值
     * @return 旧字段值
     */
    <V> V set(String key, V value);

    <V> V get(String key);

    <V> V get(String key, V defaultValue);

    /**
     * 将某个值映射为另一个值
     *
     * @param key     字段名
     * @param mapping Function
     * @return T
     */
    <S, T> T map(String key, Function<? super S, T> mapping);

    /**
     * 遍历
     *
     * @param consumer 处理逻辑
     */
    void forEach(Consumer<BeanField> consumer);
}
