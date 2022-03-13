package io.maker.base.func;

import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * @param <K>
 */
@FunctionalInterface
public interface ActionConsumer<K> {
    void apply(Supplier<K> param, Consumer<K> algorithm);
}
