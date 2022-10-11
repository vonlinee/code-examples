package io.devpl.codegen.fxui.framework;

import javafx.event.*;
import javafx.fxml.Initializable;

/**
 * 每个Controller都可以发布订阅事件
 * 订阅的事件将在FXML加载时
 * <p>
 * 实现EventTarget接口，可以通过 Event#fireEvent发送事件
 */
public abstract class FXController implements EventTarget, Initializable {

    private static final EventBus BUS = new FXEventBus();

    /**
     * 订阅事件
     * @param eventType    事件类型
     * @param eventHandler 事件处理逻辑
     * @param <T>          具体的事件
     */
    public final <T extends Event> void addEventHandler(
            final EventType<T> eventType, final EventHandler<? super T> eventHandler) {
        BUS.addEventHandler(eventType, eventHandler);
    }

    /**
     * 确保事件源是this
     * @param eventType
     * @param target
     */
    public final void fireEvent(EventTarget target, EventType<? extends Event> eventType) {
        BUS.fireEvent(new Event(this, target, eventType));
    }

    @Override
    public EventDispatchChain buildEventDispatchChain(EventDispatchChain tail) {
        return tail.prepend(new EventBusEventDispatcher(BUS));
    }
}
