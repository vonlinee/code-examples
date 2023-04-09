package io.devpl.codegen.mbpg;

import java.util.concurrent.Callable;
import java.util.function.Supplier;

public class Task<V> implements Callable<V> {

    private final Supplier<V> operation;
    private String taskName;

    public Task(String taskName, Supplier<V> operation) {
        this.taskName = taskName;
        this.operation = operation;
    }

    @Override
    public V call() throws Exception {
        V result;
        try {
            result = operation.get();
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
        return result;
    }
}
