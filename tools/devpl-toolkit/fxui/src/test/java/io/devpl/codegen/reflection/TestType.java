package io.devpl.codegen.reflection;

import io.devpl.codegen.ui.fxui.frame.FXEvent;
import javafx.event.Event;
import javafx.event.EventHandler;

import java.util.ArrayList;
import java.util.List;

public class TestType {

    static List<EventHandler<?>> handlers = new ArrayList<>();

    public static void main(String[] args) {
        addEventHandler();
        addEventHandler(new EventHandler<Event>() {
            @Override
            public void handle(Event event) {
                System.out.println(this);
            }
        });

        for (EventHandler handler : handlers) {
            handler.handle(new FXEvent());
        }
    }

    public static void addEventHandler() {
        addEventHandler(new EventHandler<>() {
            @Override
            public void handle(Event event) {
                System.out.println(this);
            }
        });
    }

    static <T extends Event> void addEventHandler(EventHandler<T> handler) {
        handlers.add(handler);
    }
}
