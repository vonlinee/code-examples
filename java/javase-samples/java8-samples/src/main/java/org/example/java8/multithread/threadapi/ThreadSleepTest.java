package org.example.java8.multithread.threadapi;

import org.example.java8.multithread.utils.Utils;

public class ThreadSleepTest {

    private static final Mutex lock = new Mutex("mutex-lock-1");

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                synchronized (lock) {
                    System.out.println(Thread.currentThread().getName() + " holds the lock " + Thread.holdsLock(lock));
                }
                Utils.sleepSeconds(10);
            }, "thread-" + i).start();
        }
    }
}
