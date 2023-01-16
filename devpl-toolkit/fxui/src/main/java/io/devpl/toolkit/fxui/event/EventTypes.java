package io.devpl.toolkit.fxui.event;

import javafx.event.Event;
import javafx.event.EventType;

/**
 * 存放用到的所有内部事件
 */
public class EventTypes {
    // 加载数据库列表
    public static final EventType<Event> ACTION_SHOW_TABLES = new EventType<>("show-tables");
    // 测试数据库连接
    public static final EventType<Event> TEST_CONNECTION = new EventType<>("test-connection");
    // 初始化数据绑定
    public static final EventType<CommandEvent> INIT_BINDER = new EventType<>("init-binder");
}
