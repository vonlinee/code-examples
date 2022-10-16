package org.greenrobot.eventbus.test;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.condition.Condition;
import org.greenrobot.eventbus.ext.EventBus;

import java.util.ArrayList;
import java.util.List;

public class Test {

    public static void main(String[] args) {
        EventBus bus = EventBus.getDefault();
        bus.register(new Test());

        List<Integer> list = List.of();

        bus.post(list);
    }

    @Subscribe(condition = Condition.NONE)
    public Integer method(Integer i) {
        System.out.println(this);
        return i + 4;
    }

    @Subscribe
    public List<String> stringList(List<String> list) {
        System.out.println("1");
        return list;
    }

    @Subscribe
    public List<String> stringList(ArrayList<String> list) {
        System.out.println("2");
        return list;
    }

    @Subscribe
    public List<Integer> integerList(List<Integer> list) {
        System.out.println("3");
        return list;
    }
}
