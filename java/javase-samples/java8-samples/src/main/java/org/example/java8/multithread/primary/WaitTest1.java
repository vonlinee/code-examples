package org.example.java8.multithread.primary;

import java.util.concurrent.locks.LockSupport;

public class WaitTest1 {

    public static void main(String[] args) {
        // 这里是以Thread作为锁对象
        ThreadA ta = new ThreadA("ta");
        synchronized (ta) { // 通过synchronized(ta)获取“对象ta的同步锁”
            try {
                ta.start();
                ta.wait(); // 线程阻塞主线程等待，必须要获取到ta这个对象锁
                // ta线程执行时会唤醒等待在ta的线程
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        LockSupport.getBlocker(ta);
    }

    static class ThreadA extends Thread {

        public ThreadA(String name) {
            super(name);
        }

        public void run() {
            // 通过synchronized(this)获取“当前对象的同步锁”
            synchronized (this) {
                // 唤醒其他线程
                notify(); // 唤醒“当前对象上的等待线程”
            }
        }
    }
}