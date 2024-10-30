package org.example.java8.multithread;

import java.util.concurrent.CountDownLatch;

public class CountDownLatchExample {
    public static void main(String[] args) {
        // 创建一个 CountDownLatch，计数为3
        CountDownLatch latch = new CountDownLatch(3);

        // 创建并启动三个线程
        for (int i = 1; i <= 3; i++) {
            final int threadNumber = i;
            new Thread(() -> {
                try {
                    // 模拟任务执行
                    System.out.println("线程 " + threadNumber + " 正在执行任务...");
                    Thread.sleep((long) (Math.random() * 1000)); // 随机休眠
                    System.out.println("线程 " + threadNumber + " 完成任务.");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    // 任务完成后计数器减1
                    latch.countDown();
                }
            }).start();
        }

        try {
            // 主线程等待，直到计数器减为0
            latch.await();
            System.out.println("所有线程已完成任务，主线程继续执行...");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}