package io.devpl.codegen.fxui;


import io.devpl.eventbus.DefaultEventBus;
import io.devpl.eventbus.ext.EventBus;

import java.util.function.Function;

public class TestMain {
    public static void main(String[] args) {
        EventBus bus = EventBus.getDefault();
        DefaultEventBus defaultEventBus = new DefaultEventBus();

        int i = 10;

    }

    static void add(Function<Integer, String> function) {

    }
}
