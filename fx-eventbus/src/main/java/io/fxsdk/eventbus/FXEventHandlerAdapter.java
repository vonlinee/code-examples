package io.fxsdk.eventbus;

import javafx.event.Event;

public interface FXEventHandlerAdapter<T extends Event, R> extends CallableEventHandler<T, R> {

    /**
     * 获取返回值
     * @return
     */
    R getReturnValue();

    /**
     * 保存某个返回值
     * @param returnValue 返回值
     */
    void setReturnValue(R returnValue);

    @Override
    default R handleAndReturn(T event) {
        handle(event);
        return getReturnValue();
    }
}
