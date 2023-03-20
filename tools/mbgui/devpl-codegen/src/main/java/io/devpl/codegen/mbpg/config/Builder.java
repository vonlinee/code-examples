package io.devpl.codegen.mbpg.config;

/**
 * 构造者标识
 * @param <T> 构建的目标类型
 */
public interface Builder<T> {

    /**
     * 构造对象逻辑
     * @return 对象实例
     */
    T build();
}
