package io.maker.codegen.core;

import java.util.EventObject;
import java.util.HashMap;
import java.util.Map;

/**
 * 事件状态对象的基类，它封装了事件源对象以及和事件相关的信息。所有java的事件类都需要继承该类。
 * 是所有事件象的基础类
 */
public class Event extends EventObject {

    private String name;
    protected Map<String, Object> attachment = new HashMap<>();

    /**
     * Constructs a prototypical Event.
     * @param source The object on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    private Event(Object source) {
        super(source);
    }

    public Event(String name, Object source) {
        super(source);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void attach(String name, Object value) {
        this.attachment.put(name, value);
    }

    public void separate(String name, Object value) {
        this.attachment.put(name, value);
    }

    public void setAttachment(Map<String, Object> map) {
        this.attachment = map;
    }

    public Map<String, Object> getAttachment() {
        return attachment;
    }
}
