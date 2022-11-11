package org.bushe.swing.test;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class Test1 {

    public static void main(String[] args) {

        EventBus bus = EventBus.getDefault();
        Test1 test1 = new Test1();
        bus.register(test1);
        long start = System.currentTimeMillis();
        bus.post(2);
        System.out.println(System.currentTimeMillis() - start);
    }


    @Subscribe
    public void method(Integer i) {
        System.out.println(i);
    }
}
