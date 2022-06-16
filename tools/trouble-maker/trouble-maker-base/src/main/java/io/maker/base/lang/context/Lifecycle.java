package io.maker.base.lang.context;

public interface Lifecycle {
    void fireLifecycle(String eventName, LifecycleContext context);
}
