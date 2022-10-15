package org.mybatis.generator.logging.internal;

import org.mybatis.generator.logging.Log;

/**
 * TODO 内部日志实现，暂时用简单的控制台打印代替
 */
public class InternalLogImpl implements Log {

    private String name;

    public InternalLogImpl(Class<?> targetClass) {
        this.name = targetClass.getName();
    }

    @Override
    public boolean isPrepared() {
        // 内部日志实现始终可用
        return true;
    }

    @Override
    public boolean isDebugEnabled() {
        return true;
    }

    @Override
    public void error(String s, Throwable e) {

    }

    @Override
    public void error(String s) {

    }

    @Override
    public void debug(String s) {
        System.out.println(s);
    }

    @Override
    public void warn(String s) {
        System.out.println(s);
    }

    @Override
    public void info(String msg) {
        System.out.println(msg);
    }

    @Override
    public void info(String format, Object arg) {
        System.out.println(String.format(format, arg));
    }

    @Override
    public void info(String format, Object arg1, Object arg2) {
        System.out.println(String.format(format, arg1, arg2));
    }

    @Override
    public void info(String format, Object... arguments) {
        System.out.println(String.format(format, arguments));
    }
}
