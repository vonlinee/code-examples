package io.devpl.toolkit.fxui.event;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventType;

/**
 * 内部事件类型
 */
public class EventTypes {

    // 加载数据库列表
    public static final EventType<Event> ACTION_SHOW_TABLES = new EventType<>("show-tables");
}
