package org.mybatis.generator.logging.nologging;

import org.mybatis.generator.logging.Log;

public class NoLoggingImpl implements Log {

    public NoLoggingImpl() {
        // Do Nothing
    }

    @Override
    public boolean isPrepared() {
        return true;
    }

    @Override
    public boolean isDebugEnabled() {
        return false;
    }

    @Override
    public void error(String s, Throwable e) {
        // Do Nothing
    }

    @Override
    public void error(String s) {
        // Do Nothing
    }

    @Override
    public void debug(String s) {
        // Do Nothing
    }

    @Override
    public void warn(String s) {
        // Do Nothing
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
