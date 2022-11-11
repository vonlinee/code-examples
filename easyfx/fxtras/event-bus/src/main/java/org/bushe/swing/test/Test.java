package org.bushe.swing.test;

import org.bushe.swing.event.EventBus;
import org.bushe.swing.event.EventService;
import org.bushe.swing.event.EventServiceLocator;
import org.bushe.swing.event.annotation.EventTopicSubscriber;

public class Test {

    public static long start;

    public static void main(String[] args) {

        EventService service = EventServiceLocator.getEventBusService();

        System.out.println(service);

        Test test = new Test();
        start = System.currentTimeMillis();
        EventBus.register(test);
        System.out.println(System.currentTimeMillis() - start);
        EventBus.publish("topic", "123");
    }

    @EventTopicSubscriber(topic = "topic")
    public void me(String topic, Object data) {
        System.out.println(System.currentTimeMillis() - start);
        System.out.println(topic);
        System.out.println(data);
    }
}
