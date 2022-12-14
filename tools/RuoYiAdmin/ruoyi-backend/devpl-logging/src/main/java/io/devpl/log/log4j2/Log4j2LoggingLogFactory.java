package io.devpl.log.log4j2;

import io.devpl.log.AbstractLogFactory;
import io.devpl.log.Log;

public class Log4j2LoggingLogFactory implements AbstractLogFactory {
    @Override
    public Log getLog(Class<?> clazz) {
        return new Log4j2Impl(clazz);
    }
}
