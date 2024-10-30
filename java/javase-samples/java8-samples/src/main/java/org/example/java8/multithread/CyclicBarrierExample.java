package org.example.java8.multithread;

import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierExample {
    public static void main(String[] args) {
        // 创建一个 CyclicBarrier，设置参与线程数量为3
        CyclicBarrier barrier = new CyclicBarrier(3, () -> {
            // 这段代码会在所有线程到达屏障后执行
            System.out.println("所有线程已到达屏障，继续执行...");
        });



        // 创建并启动三个线程
        for (int i = 1; i <= 3; i++) {
            final int threadNumber = i;
            new Thread(() -> {
                try {
                    System.out.println("线程 " + threadNumber + " 正在执行任务...");
                    Thread.sleep((long) (Math.random() * 1000)); // 模拟任务执行
                    System.out.println("线程 " + threadNumber + " 到达屏障，等待其他线程...");
                    
                    // 到达屏障，等待其他线程
                    barrier.await();
                    
                    // 继续执行后续任务
                    System.out.println("线程 " + threadNumber + " 继续执行后续任务.");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
}