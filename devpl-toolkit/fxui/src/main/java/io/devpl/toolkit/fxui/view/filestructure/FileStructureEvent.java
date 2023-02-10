package io.devpl.toolkit.fxui.view.filestructure;

import javafx.event.Event;
import javafx.event.EventType;

public class FileStructureEvent extends Event {

    public static final EventType<FileStructureEvent> CELL_UPDATED = new EventType<>("CELL_UPDATED");

    public FileStructureEvent(EventType<? extends Event> eventType) {
        super(eventType);
    }
}
