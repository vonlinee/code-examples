package org.mybatis.generator.logging;

public interface Log {

    /**
     * 日志组件是否可用，可用的标准在于是否能正常进行日志打印的功能
     * @return true
     */
    boolean isPrepared();

    boolean isDebugEnabled();

    void error(String s, Throwable e);

    void error(String s);

    void debug(String s);

    void warn(String s);

    /**
     *
     * @param msg
     */
    void info(String msg);

    /**
     *
     * @param format
     * @param arg
     */
    void info(String format, Object arg);

    /**
     *
     * @param format
     * @param arg1
     * @param arg2
     */
    void info(String format, Object arg1, Object arg2);

    /**
     *
     * @param format
     * @param arguments
     */
    void info(String format, Object... arguments);
}
