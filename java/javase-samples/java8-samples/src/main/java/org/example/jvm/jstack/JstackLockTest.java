package org.example.jvm.jstack;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class JstackLockTest {
 
    private static Executor executor = Executors.newFixedThreadPool(5);
 
    private static final Object lockA = new Object();
 
    private static final Object lockB = new Object();
 
    public static void main(String[] args) {
        executor.execute(new MyRunnableImplOne());
        executor.execute(new MyRunnableImplTwo());
    }
 
    static class MyRunnableImplOne implements Runnable {
 
        @Override
        public void run() {
            synchronized (lockA) {
                System.out.println(Thread.currentThread().getName() + "获得了锁LockA");
                //循环
                int i = 10;
                while (i > 10) {
                    i--;
                }
                synchronized (lockB) {
                    System.out.println(Thread.currentThread().getName() + "获得了锁B");
                }
            }
        }
    }
 
    static class MyRunnableImplTwo implements Runnable {
        @Override
        public void run() {
            synchronized (lockB) {
                System.out.println(Thread.currentThread().getName()+"获得了锁lockB");
                int i = 10;
                while (i > 10) {
                    i--;
                }
                synchronized (lockA) {
                    System.out.println(Thread.currentThread().getName()+"获得了锁lockA");
                }
            }
        }
    }
}