package org.example.java8.multithread.lock;

public class ThreadWait {

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
                    lock.wait(); // 释放锁，阻塞线程t1
                } catch (InterruptedException e) {
                    System.out.println("线程" + Thread.currentThread().getName() + "出现InterruptedException");
                }
                System.out.println("即将释放锁");
            }
        });
        threads[0] = new Thread(() -> {
            synchronized (lock) {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println(threads[1].getState());

                if (threads[1].getState() == Thread.State.WAITING) {
                    threads[1].interrupt();
                }
            }
        });
        threads[1].start();
    }
}
