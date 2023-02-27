package io.devpl.fxtras.mvc;

import io.devpl.fxtras.Alerts;
import io.devpl.fxtras.eventbus.JavaFXMainThreadSupport;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.EventDispatchChain;
import javafx.event.EventDispatcher;
import javafx.event.EventTarget;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.PostEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * View 基类
 */
abstract class ViewBase implements View, EventTarget, SceneGraphAccessor {

    protected Logger log = LoggerFactory.getLogger(this.getClass());

    private static final EventBus GLOBAL_EVENT_BUS = EventBus.builder()
            .eventInheritance(true)
            .allowEmptySubscriber(true)   // 是否允许空@Subsciber进行注册
            .logNoSubscriberMessages(true) // 没有订阅者时记录日志
            .mainThreadSupport(new JavaFXMainThreadSupport())
            .build();

    public ViewBase() {
        try {
            GLOBAL_EVENT_BUS.register(this);
        } catch (Exception exception) {
            // ignore
            log.error("failed to register Subscriber[{}]", this, exception);
        }
    }

    /**
     * 发布事件
     *
     * @param event 事件类型对象
     */
    public final void publish(Object event) {
        try {
            GLOBAL_EVENT_BUS.post(event);
        } catch (Exception exception) {
            Alerts.exception("发布事件异常", exception).showAndWait();
        }
    }

    /**
     * 发布事件
     *
     * @param eventName 事件名称
     * @param event     事件类型对象
     */
    public final void publish(String eventName, Object event) {
        try {
            GLOBAL_EVENT_BUS.post(new PostEvent(eventName, event));
        } catch (Exception exception) {
            Alerts.exception("发布事件异常", exception).showAndWait();
        }
    }

    /**
     * TODO 将EventHandler和EventBus进行结合
     *
     * @param tail the initial chain to build from
     * @return EventDispatchChain
     */
    @Override
    public EventDispatchChain buildEventDispatchChain(EventDispatchChain tail) {
        // 直接在初始EventDispatchChain添加上自身的EventDispatcher
        return tail.append(getInternalEventDispatcher());
    }

    /**
     * 对于Controller来说只有一个事件分派者
     */
    private ViewEventDispatcher internalEventDispatcher;

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
            eventDispatcher = new SimpleObjectProperty<>(ViewBase.this, "eventDispatcher", internalEventDispatcher);
        }
    }

    private ViewEventDispatcher createInternalEventDispatcher() {
        return new ViewEventDispatcher(this);
    }

    private ViewEventDispatcher getInternalEventDispatcher() {
        initializeInternalEventDispatcher();
        return internalEventDispatcher;
    }
}
