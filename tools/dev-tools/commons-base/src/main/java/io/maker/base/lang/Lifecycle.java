package io.maker.base.lang;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public interface Lifecycle {

    void onCreated();

    void onInitialize();

    void onChange();

    void onDestroy();

    /**
     * 自定义事件名称
     * @param event 事件名称
     */
    void fire(String event);

    default boolean exists(String event) {
        return event != null;
    }
}
