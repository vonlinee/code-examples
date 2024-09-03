package org.example.java8.multithread.juc;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class ConcurrentHashMapTest {

    @Test
    public void test1() {
        ConcurrentHashMap<String, Object> map = new ConcurrentHashMap<>();

        map.put("a", "b");
    }

    @Test
    public void test2() {
        HashMap<String, Object> map = new HashMap<>();

        map.put("a", "b");
    }

    static volatile AtomicInteger i = new AtomicInteger(0);

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            while (true) {
                if (i.getAndIncrement() == 1) {
                    System.out.println(1);
                }
            }
        });
        Thread t2 = new Thread(() -> {
            while (true) {
                if (i.getAndIncrement() == 2) {
                    System.out.println(2);
                }
            }
        });
        Thread t3 = new Thread(() -> {
            while (true) {
                if (i.getAndIncrement() == 3) {
                    System.out.println(3);
                }
            }
        });
        Thread t4 = new Thread(() -> {
            while (true) {
                if (i.getAndIncrement() == 4) {
                    System.out.println(4);
                }
            }
        });

        t1.start();
        t2.start();
        t3.start();
        t4.start();
    }
}
