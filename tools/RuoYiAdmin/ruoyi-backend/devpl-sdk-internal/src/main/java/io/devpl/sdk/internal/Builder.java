package io.devpl.sdk.internal;

/**
 * 通用的Builder模式顶级接口
 * @param <T> 通过Bulder模式要构造的类型
 */
public interface Builder<T> {
    /**
     * 构造当前定义的类型 T
     * @return T
     */
    T build();
}
