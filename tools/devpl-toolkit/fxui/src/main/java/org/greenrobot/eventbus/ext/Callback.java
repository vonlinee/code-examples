package org.greenrobot.eventbus.ext;

/**
 * 发布回调
 * @param <T>
 */
public interface Callback<T> {
    void call(T result);

    void onComplete(Object output);
}
