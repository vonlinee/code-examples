package org.example.java8.multithread.concurrency;

public class InheritableThreadLocalDemo {

    private static final ThreadLocal<String> threadLocal = new ThreadLocal<>();

    public static void main(String[] args) {
        // 主线程设置值
        threadLocal.set("mainThread");
        System.out.println("value:" + threadLocal.get());  // mainThread
        Thread thread = new Thread(() -> {
            // 子线程尝试获取
            String value = threadLocal.get();
            System.out.println("value:" + value);  // null
        });
        thread.start();
    }
}