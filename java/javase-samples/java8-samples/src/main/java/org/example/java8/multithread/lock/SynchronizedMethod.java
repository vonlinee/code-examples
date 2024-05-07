package org.example.java8.multithread.lock;

/**
 * @author vonline
 * @since 2022-07-25 19:35
 */
public class SynchronizedMethod {

    public synchronized void method() {
        System.out.println(this.getClass().getMethods().length);
    }
}
