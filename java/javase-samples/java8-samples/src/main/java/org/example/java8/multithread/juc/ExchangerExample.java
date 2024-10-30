package org.example.java8.multithread.juc;

import java.util.concurrent.Exchanger;

public class ExchangerExample {
    public static void main(String[] args) {
        Exchanger<String> exchanger = new Exchanger<>();


        // 线程1
        Thread thread1 = new Thread(() -> {
            try {
                String message1 = "Hello from Thread 1";
                // 交换数据
                String message2 = exchanger.exchange(message1);
                System.out.println("Thread 1 received: " + message2);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        // 线程2
        Thread thread2 = new Thread(() -> {
            try {
                String message1 = "Hello from Thread 2";
                // 交换数据
                String message2 = exchanger.exchange(message1);
                System.out.println("Thread 2 received: " + message2);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        // 启动线程
        thread1.start();
        thread2.start();
        
        // 等待线程结束
        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}