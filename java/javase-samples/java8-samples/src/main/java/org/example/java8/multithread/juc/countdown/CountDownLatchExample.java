package org.example.java8.multithread.juc.countdown;

import java.util.concurrent.CountDownLatch;
  
public class CountDownLatchExample {  
    public static void main(String[] args) throws InterruptedException {  
        CountDownLatch latch = new CountDownLatch(3); // 初始化计数器为3  
  
        // 创建并启动三个线程  
        for (int i = 0; i < 3; i++) {  
            new Thread(() -> {  
                try {  
                    // 模拟任务执行时间  
                    Thread.sleep(1000);  
                    System.out.println(Thread.currentThread().getName() + " 执行完毕");  
                    latch.countDown(); // 任务完成，计数器减1  
                } catch (InterruptedException e) {  
                    e.printStackTrace();  
                }  
            }).start();  
        }  
  
        // 主线程等待所有子线程完成  
        latch.await(); // 阻塞等待计数器变为0  
        System.out.println("所有线程执行完毕");  
    }  
}