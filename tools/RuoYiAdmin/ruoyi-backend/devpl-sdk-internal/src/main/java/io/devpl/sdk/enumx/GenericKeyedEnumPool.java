package io.devpl.sdk.enumx;

/**
 * key为Object类型的枚举常量池
 *
 * @param <E> 常量实例
 */
public class GenericKeyedEnumPool<E> extends KeyedEnumPool<Object, E> {

    @Override
    public E put(Object key, E instance) {
        return null;
    }
}
