package org.example.java8.multithread.practice;

import java.util.concurrent.locks.ReentrantLock;

class X {
    private final ReentrantLock lock = new ReentrantLock();

    public void m() throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            lock.lock();
        }

        lock.lockInterruptibly();;

        lock.tryLock();

        try {
            // ... method body
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) throws InterruptedException {

        X x = new X();

        x.m();
    }
}