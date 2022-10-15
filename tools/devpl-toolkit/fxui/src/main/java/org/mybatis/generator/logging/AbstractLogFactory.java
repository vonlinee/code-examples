package org.mybatis.generator.logging;

/**
 * 创建具体的日志实现
 */
public interface AbstractLogFactory {
    Log getLog(Class<?> targetClass) throws Exception;
}
