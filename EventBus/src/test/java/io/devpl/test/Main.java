package io.devpl.test;


import io.devpl.eventbus.DefaultEventBus;
import io.devpl.eventbus.EventBus;
import io.devpl.eventbus.Subscribe;
import javafx.event.Event;

import java.util.HashMap;
import java.util.Map;

/**
 * @since created on 2022年10月16日
 */
public class Main {

    public static void main(String[] args) {

        EventBus bus = new DefaultEventBus();

        Main main = new Main();

        bus.register(new Main());

        bus.publish(new Event(main, Event.NULL_SOURCE_TARGET, Event.ANY));

        System.out.println(bus.name());
    }

    @Subscribe
    public Map<String, Object> subscriber1(Event event) {

        Map<String, Object> map = new HashMap<>();
        map.put("event", event);

        return map;
    }
}
