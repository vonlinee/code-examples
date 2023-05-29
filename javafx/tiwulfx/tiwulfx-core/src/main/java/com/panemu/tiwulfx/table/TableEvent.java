package com.panemu.tiwulfx.table;

import javafx.event.Event;
import javafx.event.EventTarget;
import javafx.event.EventType;

/**
 * 表格事件
 */
public class TableEvent extends Event {

    /**
     * 表格事件
     */
    public static final EventType<TableEvent> ANY
            = new EventType<>(Event.ANY, "Table-Any");

    /**
     * 分页变更事件
     */
    public static final EventType<TableEvent> PAGE_CHANGED
            = new EventType<>(TableEvent.ANY, "Table-PageChanged");

    private int pageNum;
    private int pageSize;

    public TableEvent(Object source, EventTarget target, EventType<? extends Event> eventType) {
        super(source, target, eventType);
    }
}
