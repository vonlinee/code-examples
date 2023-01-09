package sample;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.SubscriberMethod;
import org.greenrobot.eventbus.ThreadMode;
import org.greenrobot.eventbus.extension.EventCallback;
import org.greenrobot.eventbus.extension.PostEvent;
import org.greenrobot.eventbus.extension.SubscriptionMatcher;

import java.util.function.Consumer;

public class Test {

    public static void main(String[] args) {

        final EventBus bus = EventBus.builder().logNoSubscriberMessages(false)
                                     .allowEmptySubscriber(false)
                                     .eventInheritance(false).build();

        EventBus.getDefault();

        final Test test = new Test();

        bus.register(test);

        PostEvent event = bus.expected("klsld", 2)
                             .callback(System.out::println)
                             .matcher((subscriber, subscriberMethod) -> true);

        event.post();

        System.out.println(event.getResponse());
    }

    @Subscribe(event = "klsld", threadMode = ThreadMode.BACKGROUND)
    public String post(Integer i) {
        System.out.println(111);
        return String.valueOf(System.currentTimeMillis());
    }
}
