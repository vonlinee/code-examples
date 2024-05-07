package org.example.java8.multithread.primary;

public class TestThradGroup {

    private final Thread t = new Thread(new Runnable() {
        @Override
        public void run() {
            System.out.println(this);
        }
    });

    public static void main(String[] args) {

    }

    public static void test1() {
        // main线程组
        ThreadGroup mainThreadGroup = Thread.currentThread().getThreadGroup();
        // main线程组的父线程组
        ThreadGroup systenThreadGroup = mainThreadGroup.getParent();
        System.out.println("systenThreadGroup name = " + systenThreadGroup.getName());
        System.out.println("mainThreadGroup name = " + mainThreadGroup.getName());
    }
}
