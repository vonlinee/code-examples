package org.mybatis.generator.logging.logback;

import org.mybatis.generator.logging.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * logback实现
 */
public class LogbackLogImpl implements Log {

    // ch.qos.logback.classic.Logger
    private final Logger logger;

    public LogbackLogImpl(Class<?> targetClass) {
        logger = LoggerFactory.getLogger(targetClass);
    }

    @Override
    public boolean isPrepared() {
        return logger == null || logger.isDebugEnabled();
    }

    @Override
    public boolean isDebugEnabled() {
        return false;
    }

    @Override
    public void error(String s, Throwable e) {
        logger.error(s, e);
    }

    @Override
    public void error(String s) {
        logger.error(s);
    }

    @Override
    public void debug(String s) {
        logger.debug(s);
    }

    @Override
    public void warn(String s) {
        logger.warn(s);
    }

    @Override
    public void info(String msg) {
        logger.info(msg);
    }

    @Override
    public void info(String format, Object arg) {
        logger.info(format, arg);
    }

    @Override
    public void info(String format, Object arg1, Object arg2) {
        logger.info(format, arg1, arg2);
    }

    @Override
    public void info(String format, Object... arguments) {

    }
}
