package org.example.java8.multithread.atomic;

import java.util.concurrent.atomic.AtomicInteger;

import org.example.java8.multithread.utils.Utils;

/**
 * 原子类都是基于cas进行的
 */
public class TestAtomicInt {

    static AtomicInteger integer = new AtomicInteger(0);

    public static void main(String[] args) {

        new Thread(() -> {
            while (true) {
                int i = integer.getAndIncrement();
                System.out.println(Thread.currentThread().getName() + "  " + i);
                Utils.sleepSeconds(2);
            }
        }, "add-thread-1").start();

        new Thread(() -> {
            while (true) {
                int i = integer.decrementAndGet();
                System.out.println(Thread.currentThread().getName() + "  " + i);
                Utils.sleepSeconds(2);
            }
        }, "sub-thread-1").start();
    }
}
