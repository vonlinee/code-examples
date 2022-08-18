package org.mybatis.generator.logging.internal;

import org.mybatis.generator.logging.AbstractLogFactory;
import org.mybatis.generator.logging.Log;

public class InternalLogFactory implements AbstractLogFactory {
    @Override
    public Log getLog(Class<?> targetClass) {
        return new InternalLogImpl(targetClass);
    }
}
