package org.example.java8.multithread.juc;

import java.util.concurrent.SynchronousQueue;

public class SynchronousQueueExample {

    public static void main(String[] args) {
        // 创建一个 SynchronousQueue 实例
        SynchronousQueue<String> synchronousQueue = new SynchronousQueue<>();

        // 创建一个生产者线程  
        Thread producer = new Thread(() -> {
            try {
                for (int i = 0; i < 5; i++) {
                    String item = "Item " + i;
                    System.out.println("Producer produced: " + item);
                    // 将元素放入队列
                    // 如果队列中有元素则会阻塞，直到有消费者将其取出
                    synchronousQueue.put(item);
                    Thread.sleep(1000); // 模拟生产时间  
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        // 创建一个消费者线程  
        Thread consumer = new Thread(() -> {
            try {
                for (int i = 0; i < 5; i++) {
                    // 从队列中取出元素  
                    String item = synchronousQueue.take();
                    System.out.println("Consumer consumed: " + item);
                    Thread.sleep(1500); // 模拟消费时间  
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        // 启动生产者和消费者线程  
        producer.start();
        consumer.start();
    }
}