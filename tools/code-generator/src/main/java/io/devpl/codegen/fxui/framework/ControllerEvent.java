package io.devpl.codegen.fxui.framework;

import javafx.event.Event;
import javafx.event.EventTarget;
import javafx.event.EventType;

/**
 * 控制器事件
 * @see javafx.scene.control.DialogEvent
 */
public class ControllerEvent extends BroadcastEvent {

    /**
     * Common supertype for all controller event types.
     */
    public static final EventType<ControllerEvent> ANY = new EventType<>(BroadcastEvent.ANY, "CONTROLLER");

    public static final EventType<ControllerEvent> RECEIVE_DATA = new EventType<>(ControllerEvent.ANY, "CONTROLLER_RECEIVE_DATA");

    public ControllerEvent(EventType<? extends Event> eventType) {
        super(eventType);
    }

    public ControllerEvent(Object source, EventTarget target, EventType<? extends Event> eventType) {
        super(source, target, eventType);
    }
}
