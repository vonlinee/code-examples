package io.pocket.base.func;

import java.util.function.Function;
import java.util.function.Supplier;

@FunctionalInterface
public interface Action<K, V> {
    V doAction(Supplier<K> param, Function<K, V> algorithm);
}
