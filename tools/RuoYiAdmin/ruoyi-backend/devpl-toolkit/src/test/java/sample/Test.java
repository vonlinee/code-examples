package sample;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.Subscriber;
import org.greenrobot.eventbus.ThreadMode;

import java.util.function.Consumer;

@Subscriber
public class Test {

    public static void main(String[] args) {

        final EventBus bus = EventBus.builder()
                                     .logNoSubscriberMessages(false)
                                     .allowEmptySubscriber(false)
                                     .eventInheritance(false)
                                     .build();

        final Test test = new Test();

        bus.register(test);

        bus.post(2);
    }

    @Subscribe(event = "klsld")
    public String post(Integer i) {
        System.out.println(Thread.currentThread().getName());
        throw new RuntimeException("111");
    }
}
