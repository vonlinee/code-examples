package io.devpl.toolkit.framework.mvc;

import javafx.event.*;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.greenrobot.eventbus.EventBus;
import org.mybatis.generator.logging.Log;
import org.mybatis.generator.logging.LogFactory;

/**
 * 所有控制器的基类，控制器是单例对象
 */
public abstract class AbstractViewController implements ViewController, Initializable {

    // Android的事件总线
    private static final EventBus bus = new EventBus();

    /**
     * 将自身注册进事件总线
     * 如果没有@Subscribe修饰的方法，那么会报错
     */
    public final void registerThis() {
        bus.register(this);
    }

    public final void post(Object event) {
        bus.post(event);
    }

    public final void unregister() {
        bus.unregister(this);
    }

    public final boolean hasSubscriberForEvent(Class<?> eventClass) {
        if (eventClass == null) return false;
        return bus.hasSubscriberForEvent(eventClass);
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
    public final <T extends Event> void subscribe(final EventType<T> eventType, final EventHandler<? super T> eventHandler) {
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
