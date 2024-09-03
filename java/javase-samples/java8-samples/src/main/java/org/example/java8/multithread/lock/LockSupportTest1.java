package org.example.java8.multithread.lock;

import java.util.Timer;
import java.util.concurrent.locks.LockSupport;

public class LockSupportTest1 {

    private static Thread mainThread;

    public static void main(String[] args) {
        ThreadA ta = new ThreadA();
        // 获取主线程
        mainThread = Thread.currentThread();
        ta.start();
        // 阻塞主线程
        LockSupport.park(mainThread);

    }

    static class ThreadA extends Thread {

        public void run() {
            // 唤醒主线程
            LockSupport.unpark(mainThread);

            LockSupport.park(new Object());
        }
    }

    public static void t2() throws Exception {
        Thread t = new Thread(new Runnable() {
            private int count = 0;

            @Override
            public void run() {
                long start = System.currentTimeMillis();
                long end = 0;
                while ((end - start) <= 1000) {
                    count++;
                    end = System.currentTimeMillis();
                }
                System.out.println("after 1 second.count=" + count);
                //等待或许许可
                LockSupport.park();
                System.out.println("thread over." + Thread.currentThread().isInterrupted());

            }
        });
        t.start();
        Thread.sleep(2000);
        t.interrupt(); // 中断线程
        System.out.println("main over");
    }
}
