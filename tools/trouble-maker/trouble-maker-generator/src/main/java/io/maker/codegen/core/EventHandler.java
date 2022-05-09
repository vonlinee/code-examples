package io.maker.codegen.core;

import java.util.EventListener;

public interface EventHandler<T extends Event> extends EventListener {

    /**
     * 事件处理回调
     * @param event    事件对象
     * @param callback 回调
     * @param <P>
     * @param <R>
     */
    <P, R> void handle(T event, Callback<P, R> callback);

    void handle(T event);
}
