package org.example.java8.multithread.juc;

import java.util.concurrent.CountDownLatch;

public class CountDownLatchExample {
    public static void main(String[] args) throws InterruptedException {
        final int numberOfWorkers = 3;
        CountDownLatch latch = new CountDownLatch(numberOfWorkers);
        
        for (int i = 0; i < numberOfWorkers; i++) {
            new Thread(new Worker(latch)).start();
        }
        
        // 主线程等待所有工作线程完成
        latch.await();
        System.out.println("All workers have finished their tasks.");
    }

    static class Worker implements Runnable {
        private final CountDownLatch latch;

        Worker(CountDownLatch latch) {
            this.latch = latch;
        }

        @Override
        public void run() {
            try {
                // 模拟工作
                Thread.sleep(1000);
                System.out.println(Thread.currentThread().getName() + " has finished work.");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                latch.countDown(); // 完成工作，计数减 1
            }
        }
    }
}