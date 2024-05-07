package org.example.java8.multithread.collection;

import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * https://www.jianshu.com/p/cd7a73e6bd78
 */
public class TestCopyOnWriteArrayList {

    static CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();

    public static void main(String[] args) {

        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                list.add(Thread.currentThread().getName() + " -> " + new Random().nextInt());
            }, "write-thread-" + i).start();
        }

        for (int i = 0; i < 3; i++) {
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + " -> " + list);
            }, "read-thread-" + i).start();
        }
    }
}
