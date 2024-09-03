package org.example.java8.multithread.juc;

import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExchangerExample {

    public static void main(String[] args) {
        ExecutorService executor = Executors.newCachedThreadPool();
        final Exchanger<String> exchanger = new Exchanger<>();
        executor.execute(() -> nbaTrade("克拉克森，小拉里南斯", exchanger));
        executor.execute(() -> nbaTrade("格里芬", exchanger));
        executor.execute(() -> nbaTrade("哈里斯", exchanger));
        executor.execute(() -> nbaTrade("以赛亚托马斯，弗莱", exchanger));
        executor.shutdown();
    }

    private static void nbaTrade(String data1, Exchanger<String> exchanger) {
        try {
            System.out.println(Thread.currentThread().getName() + "在交易截止之前把 " + data1 + " 交易出去");
            Thread.sleep((long) (Math.random() * 1000));
            String data2 = exchanger.exchange(data1);
            System.out.println(Thread.currentThread().getName() + "交易得到" + data2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
