package org.greenrobot.eventbus.test;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ext.EventBus;

public class Test {

    public static void main(String[] args) {

        EventBus bus = EventBus.getDefault();

        bus.register(new Test());

        bus.post(10);

    }

    @Subscribe
    public Integer method(Integer i) {
        System.out.println(this);
        return i + 4;
    }

}
