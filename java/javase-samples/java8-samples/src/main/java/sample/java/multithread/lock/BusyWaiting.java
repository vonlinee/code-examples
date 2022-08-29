package sample.java.multithread.lock;

import java.util.concurrent.locks.LockSupport;

/**
 * @author vonline
 * @since 2022-08-29 23:55
 */
public class BusyWaiting {

    static volatile int i = 0;

    public static void main(String[] args) {
        LockSupport.parkUntil(10);
    }
}
