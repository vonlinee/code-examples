package org.example.java8.multithread.primary;

import sun.nio.ch.Interruptible;

import java.lang.reflect.Field;

public class MyInterruptible implements Interruptible {
    @Override
    public void interrupt(Thread thread) {
        System.out.println(thread);
    }

    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
        Thread thread = new Thread(() -> {
            while (true) {

            }
        });
        Field blockerField = thread.getClass().getDeclaredField("blocker");
        blockerField.setAccessible(true);
        blockerField.set(thread, new MyInterruptible());
        thread.start();

        System.out.println("线程开始中断");
        thread.interrupt();
    }
}
