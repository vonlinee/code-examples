package io.devpl.codegen.fxui.framework;

import javafx.event.*;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * 每个Controller都可以发布订阅事件
 * 订阅的事件将在FXML加载时
 * <p>
 * 实现EventTarget接口，可以通过Controller向Node发送事件
 */
public abstract class FXControllerBase implements EventTarget, Initializable {

    private final EventBusEventDispatcher eventBusEventDispatcher = new EventBusEventDispatcher();

    /**
     * 订阅事件
     * @param eventType
     * @param eventHandler
     * @param <T>
     */
    public final <T extends Event> void addEventHandler(
            final EventType<T> eventType, final EventHandler<? super T> eventHandler) {
        eventBusEventDispatcher.registerEventHandler(eventType, eventHandler);
    }

    /**
     * @param tail {@code com.sun.javafx.event.EventDispatchTreeImpl}
     * @return
     */
    @Override
    public final EventDispatchChain buildEventDispatchChain(EventDispatchChain tail) {
        BroadcastEventDispatchChain chain = new BroadcastEventDispatchChain();
        chain.append(eventBusEventDispatcher);
        return chain;
    }
}
