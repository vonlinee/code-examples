package io.devpl.codegen.fxui.framework;

import javafx.event.Event;
import javafx.event.EventTarget;
import javafx.event.EventType;
import javafx.scene.Node;

public class FXEvent extends Event {

    public static final EventType<? extends FXEvent> ANY = new EventType<>(EventType.ROOT, "FXEVENT");

    public FXEvent() {
        super(null, null, ANY);
    }

    public FXEvent(EventType<? extends Event> eventType) {
        super(eventType);
    }

    public FXEvent(Object source, EventTarget target, EventType<? extends Event> eventType) {
        super(source, target, eventType);
    }

    /**
     * 仅仅使用代码触发事件的话，无法知道事件源
     * 即使指定了事件源，也会被修改
     * Event.fireEvent(btn2, new MessageEvent(btn1, btn2, MessageEvent.RECEIVE_DATA));
     * 而通过界面点击触发的就不会这样
     * <p>
     * Controller 向 Node 发送事件（EventTarget是Node），事件源会被界面的事件源覆盖，而不是代码中指定的事件源
     * <p>
     * 也就是说 View 不需要向 Controller 发送事件
     */
    public static void publish(EventTarget eventTarget, Event event) {
        // FXEvent#publish 发布事件时，如果 EventTarget 是 Node ，那么 Event 中的事件源将被真实的事件源覆盖
        // 比如: 在某个Node的事件处理函数中调用
        //    FXEvent.publish(btn2, new Event(node, obj, eventType));
        // 最终到 EventHandler 那里的 Event 的事件源是该 Node 而不是指定的 obj 对象
        // 因为界面操作是先作用于该 Node ，然后才是 FXEvent.publish 进行事件发布
        if (eventTarget instanceof Node) {
            // Controller 不用和 Node 之间通信，因w为 Controller 和 Node是耦合在一起的
            // 只需要 Controller 之间能够互相通信即可
            return;
        }
        Event.fireEvent(eventTarget, event);
    }

    /**
     * 广播事件，没有 EventTarget
     * @param event 发送的事件
     */
    public static void publish(Event event) {
        // 广播事件
        Event.fireEvent(Event.NULL_SOURCE_TARGET, event.copyFor(null, Event.NULL_SOURCE_TARGET));
    }

    public boolean isSubTypeOf(final EventType<?> subType, final EventType<?> mayBeSuperType) {
        if (subType == null) {
            return false;
        }
        EventType<?> superType = subType;
        do {
            superType = superType.getSuperType();
            if (superType == mayBeSuperType) {
                return true;
            }
        } while (superType != EventType.ROOT);
        return false;
    }
}
