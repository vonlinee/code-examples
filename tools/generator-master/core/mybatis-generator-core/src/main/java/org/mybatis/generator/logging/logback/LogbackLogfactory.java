package org.mybatis.generator.logging.logback;

import org.mybatis.generator.logging.AbstractLogFactory;
import org.mybatis.generator.logging.Log;

public class LogbackLogfactory implements AbstractLogFactory {
    @Override
    public Log getLog(Class<?> targetClass) {
        return new LogbackLogImpl(targetClass);
    }
}
