package org.example.java8.multithread.juc.aqs;

import java.util.concurrent.locks.*;
import java.util.concurrent.locks.Lock;

/**
 * https://www.baiyp.ren/CLH%E9%98%9F%E5%88%97%E9%94%81.html
 * <p>
 * https://tech.meituan.com/2019/12/05/aqs-theory-and-apply.html
 */
public class AQS {
    public static void main(String[] args) {
        AbstractQueuedSynchronizer aqs;
        Lock lock = new ReentrantLock();
        Condition condition = lock.newCondition();

        LockSupport.unpark(Thread.currentThread());

    }
}
