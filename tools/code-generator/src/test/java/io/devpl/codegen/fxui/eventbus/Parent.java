package io.devpl.codegen.fxui.eventbus;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

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

        b.post(10);
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
}

class B extends Parent {

    @Override
    public void initialize() {
        super.initialize();
    }
}