package io.devpl.eventbus.util;

import io.devpl.eventbus.EventBus;

import java.lang.reflect.Constructor;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.logging.Level;

/**
 * Executes an {@link RunnableEx} using a thread pool. Thrown exceptions are propagated by posting failure events.
 * By default, uses {@link ThrowableFailureEvent}.
 * <p>
 * Set a custom event type using {@link Builder#failureEventType(Class)}.
 * The failure event class must have a constructor with one parameter of type {@link Throwable}.
 * If using ProGuard or R8 make sure the constructor of the failure event class is kept, it is accessed via reflection.
 * E.g. add a rule like
 * <pre>
 * -keepclassmembers class com.example.CustomThrowableFailureEvent {
 *     &lt;init&gt;(java.lang.Throwable);
 * }
 * </pre>
 */
public class AsyncExecutor {

    public static class Builder {
        private Executor threadPool;
        private Class<?> failureEventType;
        private EventBus eventBus;

        private Builder() {
        }

        public Builder threadPool(Executor threadPool) {
            this.threadPool = threadPool;
            return this;
        }

        public Builder failureEventType(Class<?> failureEventType) {
            this.failureEventType = failureEventType;
            return this;
        }

        public Builder eventBus(EventBus eventBus) {
            this.eventBus = eventBus;
            return this;
        }

        public AsyncExecutor build() {
            return buildForScope(null);
        }

        public AsyncExecutor buildForScope(Object executionContext) {
            if (eventBus == null) {
                eventBus = EventBus.getDefault();
            }
            if (threadPool == null) {
                threadPool = Executors.newCachedThreadPool();
            }
            if (failureEventType == null) {
                failureEventType = ThrowableFailureEvent.class;
            }
            return new AsyncExecutor(threadPool, eventBus, failureEventType, executionContext);
        }
    }

    /**
     * Like {@link Runnable}, but the run method may throw an exception.
     */
    public interface RunnableEx {
        void run() throws Exception;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static AsyncExecutor create() {
        return new Builder().build();
    }

    private final Executor threadPool;
    private final Constructor<?> failureEventConstructor;
    private final EventBus eventBus;
    private final Object scope;

    private AsyncExecutor(Executor threadPool, EventBus eventBus, Class<?> failureEventType, Object scope) {
        this.threadPool = threadPool;
        this.eventBus = eventBus;
        this.scope = scope;
        try {
            failureEventConstructor = failureEventType.getConstructor(Throwable.class);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(
                    "Failure event class must have a constructor with one parameter of type Throwable", e);
        }
    }

    /**
     * Posts an failure event if the given {@link RunnableEx} throws an Exception.
     */
    public void execute(final RunnableEx runnable) {
        threadPool.execute(() -> {
            try {
                runnable.run();
            } catch (Exception e) {
                Object event;
                try {
                    event = failureEventConstructor.newInstance(e);
                } catch (Exception e1) {
                    eventBus.getLogger().log(Level.SEVERE, "Original exception:", e);
                    throw new RuntimeException("Could not create failure event", e1);
                }
                if (event instanceof HasExecutionScope) {
                    ((HasExecutionScope) event).setExecutionScope(scope);
                }
                eventBus.post(event);
            }
        });
    }

}
