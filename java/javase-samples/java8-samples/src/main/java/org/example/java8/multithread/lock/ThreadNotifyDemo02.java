package org.example.java8.multithread.lock;

public class ThreadNotifyDemo02 {

    static final Object lock = new Object();

    public static void main(String[] args) {
        Thread[] threads = new Thread[2];
        threads[1] = new Thread(() -> {
            synchronized (lock) {
                try {
                    if (threads[0] != null) {
                        System.out.println("启动监控线程");
                        threads[0].start();
                    }
                    lock.wait(); // 释放锁，并阻塞线程t1，线程t1不参与抢锁
                    System.out.println("线程1被唤醒");
                    Thread.sleep(4000);
                } catch (InterruptedException e) {
                    System.out.println("线程1出现InterruptedException");
                }
                System.out.println("即将释放锁");
            }
        });
        threads[0] = new Thread(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            synchronized (lock) {
                System.out.println("线程0获取到锁");
                lock.notify(); // 唤醒线程 t1
                System.out.println("线程0释放锁");
                // 如果这里t0不释放锁，那么
                try {
                    Thread.sleep(4000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        threads[1].start();
    }
}
