package org.example.java8.multithread.lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class LockTest1 {

    static final ReentrantLock lock = new ReentrantLock();
    static Condition condition = lock.newCondition();

    public static void main(String[] args) {
        // 当前线程拿到锁
        lock.lock();
        // 释放锁资源，封装当前线程为Node，扔到Condition的单向链表，可以执行其他线程方法
        try {
            condition.await();
        } catch (InterruptedException e) {
            // 中断处理
        }
        // 可以获取到锁资源
        lock.unlock();

        Thread.currentThread().interrupt();
    }

    public static void thread() {
        // 当前线程拿到锁
        lock.lock();
        // 唤醒condition中挂起的线程，执行到这之后，await挂起的线程可以被唤醒(不一定)
        condition.signal(); // condition.signalAll();

        // 释放锁资源，基于await挂起的线程，此时可以获取到锁
        lock.unlock();
    }
}
