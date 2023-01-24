package sample;

import org.greenrobot.eventbus.*;

import java.util.function.Consumer;

@Subscriber
public class Test {

    public static void main(String[] args) {

        final EventBus bus = EventBus.builder()
                .logNoSubscriberMessages(true)
                .allowEmptySubscriber(false)
                .sendNoSubscriberEvent(false)
                .eventInheritance(false)
                .build();

        final Test test = new Test();

        bus.register(test);

        bus.post(1);
    }

    @Subscribe(name = "my-event")
    public String post(Integer i) {
        System.out.println(Thread.currentThread()
                .getName());
        System.out.println("post " + i);
        return "this";
    }

    @Subscribe
    public String post1(Integer i) {
        System.out.println(Thread.currentThread()
                .getName());
        System.out.println("post1 " + i);
        return "this";
    }
}
