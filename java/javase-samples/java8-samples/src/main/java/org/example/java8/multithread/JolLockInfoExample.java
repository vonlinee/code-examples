package org.example.java8.multithread;

import org.openjdk.jol.info.ClassLayout;

public class JolLockInfoExample {
    private final Object lock = new Object();

    public static void main(String[] args) {
        JolLockInfoExample example = new JolLockInfoExample();

        // 获取对象的锁信息
        System.out.println("Initial lock info:");
        printLockInfo(example.lock);

        // 获取锁
        synchronized (example.lock) {
            System.out.println("Lock acquired:");
            printLockInfo(example.lock);
        }

        System.out.println("Lock released:");
        printLockInfo(example.lock);
    }

    private static void printLockInfo(Object obj) {
        System.out.println(ClassLayout.parseInstance(obj).toPrintable());
    }
}