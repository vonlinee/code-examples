package org.example.java8.collections.map;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class HashMapDeadLock {

    public static void main(String[] args) {
        for (int i = 0; i < 5; i++) {
            new HashMapThread().start();
        }
    }
}

class HashMapThread extends Thread {
    private static final AtomicInteger ai = new AtomicInteger();
    private static final Map<Integer, Integer> map = new HashMap<>();

    @Override
    public void run() {
        while (ai.get() < 1000000) {
            map.put(ai.get(), ai.get());
            ai.incrementAndGet();
        }
    }
}