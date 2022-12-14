package io.devpl.log.slf4j;

import io.devpl.log.AbstractLogFactory;
import io.devpl.log.Log;

public class Slf4jLoggingLogFactory implements AbstractLogFactory {
    @Override
    public Log getLog(Class<?> clazz) {
        return new Slf4jImpl(clazz);
    }
}