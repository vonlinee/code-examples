package org.example.java8.multithread.concurrency;

public class InheritableThreadLocalDemo1 {

    private static final InheritableThreadLocal<String> threadLocal = new InheritableThreadLocal<>();

    public static void main(String[] args) {
        threadLocal.set("mainThread");
        System.out.println("value:" + threadLocal.get());
        Thread thread = new Thread(() -> {
            String value = threadLocal.get();
            System.out.println("value:" + value);
        });
        thread.start();
    }
}