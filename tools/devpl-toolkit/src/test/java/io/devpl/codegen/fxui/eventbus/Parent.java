package io.devpl.codegen.fxui.eventbus;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

public abstract class Parent {

    static EventBus bus = new EventBus();

    private void registerThis() {
        bus.register(this);
    }

    public final void post(Object event) {
        bus.post(event);
    }

    public void initialize() {
        registerThis();
    }

    public static void main(String[] args) {
        A a = new A();
        B b = new B();
        a.initialize();
        b.initialize();

        List<Object> list = List.of(1, new Object());

        b.post(list);
    }
}

class A extends Parent {

    @Override
    public void initialize() {
        super.initialize();
    }

    @Subscribe
    public void response(Integer param) {
        System.out.println(this + "  " + param);
    }

    @Subscribe
    public void list1(List<Integer> list) {
        System.out.println(list);
    }

    @Subscribe
    public void list2(List<String> list) {
        System.out.println(list);
    }
}

class B extends Parent {

    @Override
    public void initialize() {
        super.initialize();
    }
}