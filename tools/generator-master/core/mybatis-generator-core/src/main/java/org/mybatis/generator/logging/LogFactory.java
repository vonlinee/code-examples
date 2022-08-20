package org.mybatis.generator.logging;

import static org.mybatis.generator.internal.util.messages.Messages.getString;

import org.mybatis.generator.internal.util.JavaVersion;
import org.mybatis.generator.internal.util.RuntimeUtils;
import org.mybatis.generator.logging.commons.JakartaCommonsLoggingLogFactory;
import org.mybatis.generator.logging.internal.InternalLogFactory;
import org.mybatis.generator.logging.jdk14.Jdk14LoggingLogFactory;
import org.mybatis.generator.logging.log4j2.Log4j2LoggingLogFactory;
import org.mybatis.generator.logging.logback.LogbackLogfactory;
import org.mybatis.generator.logging.nologging.NoLoggingLogFactory;
import org.mybatis.generator.logging.slf4j.Slf4jLoggingLogFactory;

public class LogFactory {
    private static AbstractLogFactory theFactory;
    public static final String MARKER = "MYBATIS-GENERATOR"; //$NON-NLS-1$

    /**
     * 是否使用内部的日志
     */
    private static final boolean useInternalLogImpl = System.getProperty("useInternalLogImpl") == null;

    static {
        tryMultiLogImpl();
    }

    private static void tryMultiLogImpl() {
        if (tryImplementation(new Slf4jLoggingLogFactory())) {
            System.out.println("Slf4jLoggingLogFactory");
            return;
        }
        if (tryImplementation(new LogbackLogfactory())) {
            System.out.println("=======================");
        }
        if (tryImplementation(new JakartaCommonsLoggingLogFactory())) {
            System.out.println("JakartaCommonsLoggingLogFactory");
            return;
        }
        if (tryImplementation(new Log4j2LoggingLogFactory())) {
            System.out.println("Log4j2LoggingLogFactory");
            return;
        }
        // JDK >= 14
        if (RuntimeUtils.getJvmVersion().compareTo(JavaVersion.JAVA_14) >= 0 && tryImplementation(new Jdk14LoggingLogFactory())) {
            return;
        }
        if (useInternalLogImpl && tryImplementation(new InternalLogFactory())) {
            // 使用内部日志实现
            return;
        }
        if (tryImplementation(new NoLoggingLogFactory())) {
            System.err.println("[WARNING " + MARKER + "] Logging initialized using instance of '" + NoLoggingLogFactory.class + "' adapter.");
        }
    }

    private LogFactory() {
    }

    public static Log getLog(Class<?> clazz) {
        try {
            return theFactory.getLog(clazz);
        } catch (Exception t) {
            throw new RuntimeException(getString("RuntimeError.21", //$NON-NLS-1$
                    clazz.getName(), t.getMessage()), t);
        }
    }

    /**
     * This method will switch the logging implementation to Java native
     * logging. This is useful in situations where you want to use Java native
     * logging to log activity but Log4J is on the classpath. Note that this
     * method is only effective for log classes obtained after calling this
     * method. If you intend to use this method you should call it before
     * calling any other method.
     */
    public static synchronized void forceJavaLogging() {
        setImplementation(new Jdk14LoggingLogFactory());
    }

    public static synchronized void forceSlf4jLogging() {
        setImplementation(new Slf4jLoggingLogFactory());
    }

    public static synchronized void forceCommonsLogging() {
        setImplementation(new JakartaCommonsLoggingLogFactory());
    }

    public static synchronized void forceLog4j2Logging() {
        setImplementation(new Log4j2LoggingLogFactory());
    }

    public static synchronized void forceNoLogging() {
        setImplementation(new NoLoggingLogFactory());
    }

    public static void setLogFactory(AbstractLogFactory logFactory) {
        setImplementation(logFactory);
    }

    private static boolean tryImplementation(AbstractLogFactory factory) {
        if (theFactory == null) {
            try {
                // 为theFactory赋值
                return setImplementation(factory);
            } catch (LogException e) {
                // ignore
                return false;
            }
        }
        return true;
    }

    private static boolean setImplementation(AbstractLogFactory factory) {
        try {
            // 获取LogFactory门面，如果获取成功
            Log log = factory.getLog(LogFactory.class); // 可能抛异常
            if (!log.isPrepared()) {
                return false;
            }
            log.debug("Logging initialized using '" + factory + "' adapter."); //$NON-NLS-1$ //$NON-NLS-2$
            theFactory = factory;
        } catch (Throwable t) {
            throw new LogException("Error setting Log implementation.  Cause: " + t.getMessage(), t); //$NON-NLS-1$
        }
        return true;
    }
}
