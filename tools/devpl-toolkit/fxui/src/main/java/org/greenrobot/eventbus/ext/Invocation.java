package org.greenrobot.eventbus.ext;

/**
 * 一段调用逻辑
 *
 * @param <T> 入参
 * @param <R> 返回值
 */
public interface Invocation<T, R> {
    R invoke(T input) throws Exception;
}
