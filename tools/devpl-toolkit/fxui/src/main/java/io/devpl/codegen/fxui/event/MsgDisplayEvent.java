package io.devpl.codegen.fxui.event;

import javafx.event.Event;
import javafx.event.EventTarget;
import javafx.event.EventType;
import javafx.stage.Stage;

import java.util.Objects;

public class MsgDisplayEvent extends Event {

    public static final EventType<? extends MsgDisplayEvent> DISPLAY_MSG = new EventType<>(EventType.ROOT, "DISPALY_MESSAGE");

    private Stage ownerStage;
    private String title;
    private String content;

    public MsgDisplayEvent(Stage ownerStage, String title, String content) {
        this(null, Event.NULL_SOURCE_TARGET, DISPLAY_MSG);
        this.ownerStage = Objects.requireNonNull(ownerStage, "owner stage cannot be null");
        this.title = title;
        this.content = content;
    }

    public MsgDisplayEvent(Object source, EventTarget target, EventType<? extends Event> eventType) {
        super(source, target, eventType);
    }

    public Stage getOwnerStage() {
        return ownerStage;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }
}
