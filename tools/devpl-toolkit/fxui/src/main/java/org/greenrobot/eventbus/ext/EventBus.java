package org.greenrobot.eventbus.ext;

import org.greenrobot.eventbus.DefaultEventBus;
import org.greenrobot.eventbus.EventBusConfiguration;

/**
 * 对EventBus的思考: https://www.jianshu.com/p/e41e580eff10
 *
 * 1.EventBus是不支持post null的，那么之前，在回调中，有使用到null来进行判断的逻辑就得改了
 */
public interface EventBus {

    void post(Object event);

    void register(Object subscriber);

    boolean isRegistered(Object subscriber);

    void unregister(Object subscriber);

    EventBusConfiguration getConfiuration();

    /**
     * Convenience singleton for apps using a process-wide EventBus instance.
     */
    static EventBus getDefault() {
        return DefaultEventBus.getDefault();
    }
}
