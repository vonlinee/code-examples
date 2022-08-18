package org.mybatis.generator.logging.internal;

import org.mybatis.generator.logging.Log;

public class InternalLogImpl implements Log {

    public InternalLogImpl(Class<?> targetClass) {

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

    }

    @Override
    public void error(String s) {

    }

    @Override
    public void debug(String s) {

    }

    @Override
    public void warn(String s) {

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
