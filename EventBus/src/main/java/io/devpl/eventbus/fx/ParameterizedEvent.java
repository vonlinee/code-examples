package io.devpl.eventbus.fx;

import javafx.event.EventType;

/**
 * 以方法参数的Class作为事件类型区分，而不是具体的事件类
 */
public final class ParameterizedEvent extends FXEvent {

    static final EventType<? extends FXEvent> EVENT_REGISTRATION
            = new EventType<>(FXEvent.ANY, "EVENT_REGISTRATION");

    static final EventType<? extends FXEvent> EVENT_PUBLISH
            = new EventType<>(FXEvent.ANY, "EVENT_PUBLISH");
}
