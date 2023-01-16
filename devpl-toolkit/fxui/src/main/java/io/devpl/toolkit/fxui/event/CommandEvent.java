package io.devpl.toolkit.fxui.event;

import javafx.event.Event;
import javafx.event.EventTarget;
import javafx.event.EventType;

/**
 * 执行某种操作的事件
 */
public class CommandEvent extends Event {

    private static final long serialVersionUID = -7087734786834852265L;
    // 通用命令
    public static final EventType<CommandEvent> COMMAND = new EventType<>(EventType.ROOT, "root-commant");
    // 选择事件
    public static final EventType<CommandEvent> TABLES_SELECTED = new EventType<>(CommandEvent.COMMAND, "tables-selected");
    // 打开数据库连接窗口
    public static final EventType<CommandEvent> OPEN_DB_CONNECTION = new EventType<>(CommandEvent.COMMAND, "open-db-connection");

    private String command;
    private Object data;

    public CommandEvent(EventType<? extends Event> eventType, Object data) {
        this(eventType);
        this.data = data;
    }

    public CommandEvent(EventType<? extends Event> eventType) {
        super(eventType);
        this.command = eventType.getName();
    }

    public CommandEvent(EventType<? extends Event> eventType, String command, Object data) {
        super(eventType);
        this.command = command;
        this.data = data;
    }

    public CommandEvent(Object source, EventTarget target, EventType<? extends Event> eventType) {
        super(source, target, eventType);
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @SuppressWarnings("unchecked")
    public <T> T getTypedValue(Class<T> type) {
        return (T) data;
    }
}
