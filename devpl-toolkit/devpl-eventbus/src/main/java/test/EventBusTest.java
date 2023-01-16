package test;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.EventCallback;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.Subscriber;

public class EventBusTest {

    static class SubscriberClass {

        @Subscribe
        public String handle(Integer i) {
            return String.valueOf(i);
        }
    }

    public static void main(String[] args) {

        final EventBus bus = EventBus.builder()
                                     .eventInheritance(false)
                                     .allowEmptySubscriber(true)
                                     .logNoSubscriberMessages(false)
                                     .build();

        final SubscriberClass obj = new SubscriberClass();

        bus.register(obj);

        bus.post(10);
    }
}


