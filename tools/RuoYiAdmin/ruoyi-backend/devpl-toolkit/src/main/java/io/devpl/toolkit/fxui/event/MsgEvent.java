package io.devpl.toolkit.fxui.event;

import javafx.event.Event;
import javafx.event.EventType;

public class MsgEvent extends Event {

    private static final long serialVersionUID = 8024457898106837566L;

    private Object data;

    public MsgEvent(EventType<? extends Event> eventType, Object data) {
        super(eventType);
        this.data = data;
    }

    public MsgEvent(EventType<? extends Event> eventType) {
        super(eventType);
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
