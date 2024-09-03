package org.example.jvm.heap;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.LockSupport;

/**
 * -Xms10m -Xmx10m
 */
public class OOMTest1 {

    static final List<byte[]> list = new ArrayList<>();

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            while (true) {
                byte[] bytes = new byte[1024 * 1024]; // 1次1M
                list.add(bytes);
            }
        });

        Thread t2 = new Thread(() -> {
            while (true) {
                System.out.println(Thread.currentThread().getName() + " is running!");
            }
        });
        t1.setName("t1");
        t2.setName("t2");
        t1.start();
        t2.start();

        LockSupport.park();
    }
}