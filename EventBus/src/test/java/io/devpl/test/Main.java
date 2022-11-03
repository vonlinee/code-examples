package io.devpl.test;


import io.devpl.eventbus.DefaultEventBus;
import io.devpl.eventbus.EventBus;
import io.devpl.eventbus.Subscribe;
import javafx.event.Event;
import javafx.event.EventDispatchChain;
import javafx.event.EventTarget;

import java.util.HashMap;
import java.util.Map;

/**
 * @since created on 2022年10月16日
 */
public class Main implements EventTarget {

    public static void main(String[] args) {

        EventBus bus = new DefaultEventBus();

        Main main = new Main();

        bus.register(main);

        Event event = new Event(main, Event.NULL_SOURCE_TARGET, Event.ANY);

        bus.publish("subscriber1", event);

        Event.fireEvent(main, event);
    }

    @Subscribe(priority = 2, topic = "subscriber1")
    public Map<String, Object> subscriber1(Event event) {
        Map<String, Object> map = new HashMap<>();
        map.put("event", event);
        System.out.println("11111111111");
        return map;
    }

    @Subscribe(priority = 1, topic = "subscriber1")
    public Map<String, Object> subscriber2(Event event) {
        Map<String, Object> map = new HashMap<>();
        map.put("event", event);
        System.out.println("22222222222");
        return map;
    }

    @Override
    public EventDispatchChain buildEventDispatchChain(EventDispatchChain tail) {
        System.out.println(tail);
        return tail;
    }
}
