package io.maker.base.lang;

public interface Holder<T> {

    void hold(T something);

    void action();

    T get();
}