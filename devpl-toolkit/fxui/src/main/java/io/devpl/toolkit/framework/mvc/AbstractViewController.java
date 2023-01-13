package io.devpl.toolkit.framework.mvc;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.*;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscriber;
import org.mybatis.generator.logging.Log;
import org.mybatis.generator.logging.LogFactory;

/**
 * 所有控制器的基类，控制器是单例对象
 */
@Subscriber
public abstract class AbstractViewController implements ViewController, EventTarget, Initializable {

    protected final Log log = LogFactory.getLog(getClass());

    // 全局事件总线
    private static final EventBus bus = EventBus.builder()
            .logNoSubscriberMessages(true)
            .allowEmptySubscriber(true)  // 允许没有@Subscribe方法
            .build();

    /**
     * 将自身注册进事件总线
     * 如果没有@Subscribe修饰的方法，那么会报错
     */
    public final void registerThis() {
        bus.register(this);
    }

    public final void publish(Object event) {
        bus.post(event);
    }

    public final void unregister() {
        bus.unregister(this);
    }

    /**
     * 从事件获取当前事件源所在的舞台对象
     * When accessing a Stage, timing is important, as the Stage is not created
     * until the very end of a View-creation process.
     * <a href="https://edencoding.com/stage-controller/">...</a>
     * @param event JavaFX event
     * @return 当前事件源所在的舞台对象
     * @throws RuntimeException 如果事件源不是Node
     */
    protected Stage getStage(Event event) {
        final Object source = event.getSource();
        if (source instanceof Node) {
            final Node node = (Node) source;
            return getStage(node);
        }
        throw new RuntimeException("event source is [" + source.getClass() + "] instead of a [Node]");
    }

    public Stage getStage(Node node) {
        final Scene scene = node.getScene();
        if (scene == null) {
            throw new RuntimeException("node [" + node + "] has not been bind to a scene!");
        }
        final Window window = scene.getWindow();
        if (window instanceof Stage) {
            return (Stage) window;
        }
        throw new RuntimeException("the window [" + window + "] is not a stage");
    }

    @Override
    public EventDispatchChain buildEventDispatchChain(EventDispatchChain tail) {
        return tail.append(getInternalEventDispatcher());
    }

    /**
     * 对于Controller来说只有一个事件分派者
     */
    private ViewControllerEventDispatcher internalEventDispatcher;

    public final ObjectProperty<EventDispatcher> eventDispatcherProperty() {
        initializeInternalEventDispatcher();
        return eventDispatcher;
    }

    /**
     * Specifies the event dispatcher for this node. The default event
     * dispatcher sends the received events to the registered event handlers and
     * filters. When replacing the value with a new {@code EventDispatcher},
     * the new dispatcher should forward events to the replaced dispatcher
     * to maintain the node's default event handling behavior.
     */
    private ObjectProperty<EventDispatcher> eventDispatcher;

    private void initializeInternalEventDispatcher() {
        if (internalEventDispatcher == null) {
            internalEventDispatcher = createInternalEventDispatcher();
            eventDispatcher = new SimpleObjectProperty<>(
                    AbstractViewController.this,
                    "eventDispatcher",
                    internalEventDispatcher);
        }
    }

    private ViewControllerEventDispatcher createInternalEventDispatcher() {
        return new ViewControllerEventDispatcher(this);
    }

    private ViewControllerEventDispatcher getInternalEventDispatcher() {
        initializeInternalEventDispatcher();
        return internalEventDispatcher;
    }

    /**
     * Sets the handler to use for this event type. There can only be one such handler
     * specified at a time. This handler is guaranteed to be called as the last, after
     * handlers added using { #addEventHandler(javafx.event.EventType, javafx.event.EventHandler)}.
     * This is used for registering the user-defined onFoo event handlers.
     * @param <T>          the specific event class of the handler
     * @param eventType    the event type to associate with the given eventHandler
     * @param eventHandler the handler to register, or null to unregister
     * @throws NullPointerException if the event type is null
     */
    protected final <T extends Event> void addEventHandler(
            final EventType<T> eventType,
            final EventHandler<? super T> eventHandler) {
        getInternalEventDispatcher().addEventHandler(eventType, eventHandler);
    }

    public final void fireEvent(Event event) {
        Event.fireEvent(this, event);
    }

    public final <T extends Event> void fireEvent(EventType<T> eventType) {
        fireEvent(new Event(this, this, eventType));
    }
}
