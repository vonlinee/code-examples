package io.devpl.eventbus.ext;

/**
 * 保存返回值
 * @param <T>
 */
public interface CallableEvent<T> {

    void setResult(T result);

    T getResult();
}
