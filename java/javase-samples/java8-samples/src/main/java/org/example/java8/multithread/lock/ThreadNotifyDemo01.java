package org.example.java8.multithread.lock;

public class ThreadNotifyDemo01 {

    static final Object lock = new Object();

    public static void main(String[] args) {
        lock.notify();
    }
}
