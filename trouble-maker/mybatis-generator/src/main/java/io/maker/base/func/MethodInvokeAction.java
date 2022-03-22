package io.maker.base.func;

import java.util.function.Function;
import java.util.function.Supplier;

public abstract class MethodInvokeAction<T> implements Action<MethodParam, T> {

    @Override
    public T doAction(Supplier<MethodParam> param, Function<MethodParam, T> algorithm) {
        return null;
    }
}
