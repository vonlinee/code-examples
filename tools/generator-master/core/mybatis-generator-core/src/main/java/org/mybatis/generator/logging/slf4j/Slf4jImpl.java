package org.mybatis.generator.logging.slf4j;

import org.mybatis.generator.internal.util.ClassloaderUtils;
import org.mybatis.generator.logging.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.helpers.NOPLogger;
import org.slf4j.spi.LocationAwareLogger;

public class Slf4jImpl implements Log {

    private Log log;

    private boolean prepared;

    @Override
    public boolean isPrepared() {
        return prepared;
    }

    public Slf4jImpl(Class<?> clazz) {
        // 优先于slf4j自身进行检查
        // StaticLoggerBinder不存在，则slf4j没有实现
        if (!ClassloaderUtils.isClassExists("org.slf4j.impl.StaticLoggerBinder")) {
            prepared = false; // 初始化失败
            return;
        }
        // SLF4j门面可能有不同实现
        Logger logger = LoggerFactory.getLogger(clazz);
        if (logger instanceof NOPLogger) { // 针对slf4j-log4j12
            // 未初始化正确，比如未配置
            prepared = false;
            return;
        }
        // org.slf4j.spi.LocationAwareLogger
        if (logger instanceof LocationAwareLogger) {
            try {
                // check for slf4j >= 1.6 method signature
                logger.getClass().getMethod("log", Marker.class, String.class, int.class, //$NON-NLS-1$
                        String.class, Object[].class, Throwable.class);
                log = new Slf4jLocationAwareLoggerImpl((LocationAwareLogger) logger);
                prepared = true;
                return;
            } catch (SecurityException | NoSuchMethodException e) {
                // fail-back to Slf4jLoggerImpl
            }
        }
        // Logger is not LocationAwareLogger or slf4j version < 1.6
        log = new Slf4jLoggerImpl(logger);
    }

    @Override
    public boolean isDebugEnabled() {
        return log.isDebugEnabled();
    }

    @Override
    public void error(String s, Throwable e) {
        log.error(s, e);
    }

    @Override
    public void error(String s) {
        log.error(s);
    }

    @Override
    public void debug(String s) {
        log.debug(s);
    }

    @Override
    public void warn(String s) {
        log.warn(s);
    }

    @Override
    public void info(String msg) {

    }

    @Override
    public void info(String format, Object arg) {

    }

    @Override
    public void info(String format, Object arg1, Object arg2) {

    }

    @Override
    public void info(String format, Object... arguments) {

    }

}
