package org.example.java8.multithread.threadapi;

import javafx.scene.control.TreeView;

public class ThreadPriorityTest {

    public static void main(String[] args) throws ClassNotFoundException {

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

            }
        });

        ClassLoader loader = Thread.currentThread().getContextClassLoader();

        Class<?> clazz = loader.loadClass(Thread.class.getName());

    }
}
