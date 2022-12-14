package io.devpl.log.jdk14;

import io.devpl.log.AbstractLogFactory;
import io.devpl.log.Log;

public class Jdk14LoggingLogFactory implements AbstractLogFactory {
    @Override
    public Log getLog(Class<?> clazz) {
        return new Jdk14LoggingImpl(clazz);
    }
}