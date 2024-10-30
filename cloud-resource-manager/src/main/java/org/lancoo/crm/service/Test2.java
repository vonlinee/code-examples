package org.lancoo.crm.service;

import java.util.concurrent.atomic.AtomicReference;

public class Test2 {

    static final AtomicReference<Thread> ar = new AtomicReference<>();

    public static void main(String[] args) {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(ar.get() == Thread.currentThread());
                Thread.currentThread().interrupt();
                System.out.println("after Thread.currentThread().interrupt()");
            }
        });
        ar.set(t);
        t.start();

        while (true) {

        }
    }
}
