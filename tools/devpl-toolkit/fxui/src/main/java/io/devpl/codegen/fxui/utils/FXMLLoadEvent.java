package io.devpl.codegen.fxui.utils;

import javafx.event.Event;
import javafx.event.EventTarget;
import javafx.event.EventType;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FXMLLoadEvent extends Event {

    private String fxmlLocation;
    private FXMLLoader loader;
    private Parent parent;
    private Object controller;
    private boolean useCache;

    public FXMLLoadEvent(EventType<? extends Event> eventType) {
        super(eventType);
    }

    public FXMLLoadEvent(Object source, EventTarget target, EventType<? extends Event> eventType) {
        super(source, target, eventType);
    }
}
