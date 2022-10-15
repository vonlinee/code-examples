package org.greenrobot.eventbus.ext;

@FunctionalInterface
public interface Callback<T> {
    void call(T result);
}
