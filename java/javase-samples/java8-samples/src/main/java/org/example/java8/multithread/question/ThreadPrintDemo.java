package org.example.java8.multithread.question;

import java.util.concurrent.atomic.AtomicInteger;

public class ThreadPrintDemo {
    static AtomicInteger cxsNum = new AtomicInteger(0);
    static volatile boolean flag = false;

    public static void main(String[] args) {
        new Thread(() -> {
            while (100 > cxsNum.get()) {
                if (!flag && (cxsNum.get() == 0 || cxsNum.incrementAndGet() % 2 == 0)) {
                    try {
                        Thread.sleep(100);// 防止打印速度过快导致混乱
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(cxsNum.get());
                    flag = true;
                }
            }
        }).start();
        new Thread(() -> {
            while (100 > cxsNum.get()) {
                if (flag && (cxsNum.incrementAndGet() % 2 != 0)) {
                    try {
                        Thread.sleep(100);// 防止打印速度过快导致混乱
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(cxsNum.get());
                    flag = false;
                }
            }
        }).start();
    }
}