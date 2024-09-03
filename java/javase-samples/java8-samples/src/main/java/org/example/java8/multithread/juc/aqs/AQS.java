package org.example.java8.multithread.juc.aqs;

import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * https://www.baiyp.ren/CLH%E9%98%9F%E5%88%97%E9%94%81.html
 * <p>
 * https://tech.meituan.com/2019/12/05/aqs-theory-and-apply.html
 */
public class AQS {
    public static void main(String[] args) {
        AbstractQueuedSynchronizer aqs;
        Lock lock = new ReentrantLock();

        try {
            lock.lock();

            Condition condition = lock.newCondition();

        } finally {
            lock.unlock();
        }
    }
}
