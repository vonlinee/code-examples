package io.devpl.toolkit.framework.mvc;

import javafx.event.*;
import org.mybatis.generator.logging.Log;
import org.mybatis.generator.logging.LogFactory;

public abstract class AbstractViewController implements ViewController {

    protected final Log log = LogFactory.getLog(getClass());

    /**
     * 事件分派者链
     */
    private static final ViewControllerEventDispatchChain viewControllerDispatcherChain = new ViewControllerEventDispatchChain();

    @Override
    public EventDispatchChain buildEventDispatchChain(EventDispatchChain tail) {
        return viewControllerDispatcherChain;
    }

    /**
     * 对于Controller来说只有一个事件分派者
     */
    private final ViewControllerEventDispatcher eventDispatcher;

    public AbstractViewController() {
        eventDispatcher = new ViewControllerEventDispatcher();
        viewControllerDispatcherChain.append(eventDispatcher);
    }

    @Override
    public EventDispatcher getEventDispatcher() {
        return eventDispatcher;
    }

    /**
     * Registers an event handler to this node. The handler is called when the
     * node receives an {@code Event} of the specified type during the bubbling
     * phase of event delivery.
     * @param <T>          the specific event class of the handler
     * @param eventType    the type of the events to receive by the handler
     * @param eventHandler the handler to register
     * @throws NullPointerException if the event type or handler is null
     */
    public final <T extends Event> void subscribe(
            final EventType<T> eventType,
            final EventHandler<? super T> eventHandler) {
        final String name = eventType.getName();
        if (name == null || name.length() == 0) {
            throw new IllegalArgumentException("event type cannot be empty");
        }
        eventDispatcher.addEventHandler(eventType, eventHandler);
    }

    /**
     * 全局事件
     * @param event 事件
     */
    public final void publish(Event event) {
        EventTarget target = event.getTarget();
        if (target == null || target == Event.NULL_SOURCE_TARGET) {
            target = this;
        }
        Object source = event.getSource();
        if (source != this) {
            source = this;
        }
        Event.fireEvent(this, event.copyFor(source, target));
    }
}
