package org.example.java8.multithread.juc;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class SemaphoreExample {
    private static final int MAX_AVAILABLE = 3; // 最大可用许可证
    private static Semaphore semaphore = new Semaphore(MAX_AVAILABLE, true); // 公平模式

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            new Thread(new Worker(i)).start();
        }
    }

    static class Worker implements Runnable {
        private int id;

        Worker(int id) {
            this.id = id;
        }

        @Override
        public void run() {
            try {
                System.out.println("Worker " + id + " is trying to acquire a permit.");
                semaphore.acquire(); // 获取许可证

                semaphore.tryAcquire(3000, TimeUnit.SECONDS);

                System.out.println("Worker " + id + " acquired a permit.");

                // 模拟工作
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                System.out.println("Worker " + id + " is releasing the permit.");
                semaphore.release(); // 释放许可证
            }
        }
    }
}