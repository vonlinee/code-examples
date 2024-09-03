package org.example.rabbitmq;

public class JStackTest {
    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            while (true) {
                System.out.println("thread 1");
            }
        });
        Thread t2 = new Thread(() -> {
            while (true) {
                System.out.println("thread 2");
            }
        });
        t1.setName("t1");
        t2.setName("t2");
        t1.start();
        t2.start();
    }
}