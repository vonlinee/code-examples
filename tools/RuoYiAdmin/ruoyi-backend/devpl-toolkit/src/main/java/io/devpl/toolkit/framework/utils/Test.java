package io.devpl.toolkit.framework.utils;

public class Test {

    static WeakValueHashMap<String, Object> map = new WeakValueHashMap<>();

    public static void main(String[] args) {
        new Thread(() -> {
            for (int i = 0; i < 1000000; i++) {
                map.put(String.valueOf(i), i);
            }
        }).start();
    }

    static final Object lock = new Object();

    public static void test2() {
        new Thread(() -> {
            lock.notifyAll();
            try {
                lock.wait();
            } catch (InterruptedException exception) {
                exception.printStackTrace();
            }
        });
        new Thread(() -> {

        }).start();
    }

    public static void setMap() {

        new ProcessBuilder();

    }
}
