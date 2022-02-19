package io.maker.base.func;

import java.util.function.Function;
import java.util.function.Supplier;

/**
 * 参数和算法逻辑都由参数传入
 * @param <K>
 * @param <V>
 */
@FunctionalInterface
public interface Action<K, V> {
    V apply(Supplier<K> param, Function<K, V> algorithm);

    default V action(Supplier<K> param, Function<K, V> algorithm) {
        return algorithm.apply(param.get());
    }
}
