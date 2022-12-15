package io.devpl.test;

import io.devpl.eventbus.DefaultEventBus;
import io.devpl.eventbus.Subscribe;
import javafx.event.Event;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

/**
 * @since created on 2022年10月16日
 */
public class Main {

    static long start = 0;

    public static void main(String[] args) {

        DefaultEventBus bus = DefaultEventBus.builder()
                .allowHasNoSubscribeMethod(true)
                .sendNoSubscriberEvent(false)
                .logNoSubscriberMessages(false)
                .build();

        Main main = new Main();

        bus.register(main);

        Event event = new Event(main, Event.NULL_SOURCE_TARGET, Event.ANY);
        start = System.currentTimeMillis();
        bus.publish("subscriber1", event);


    }

    @Subscribe(priority = 2, topic = "subscriber1")
    public Map<String, Object> subscriber1(Event event) {
        System.out.println((System.currentTimeMillis() - start) + " ms");
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
}
