package sample.java.multithread.lock;

import java.util.concurrent.locks.LockSupport;

public class TestLockSupport {

    public static void main(String[] args) {
        LockSupport.unpark(Thread.currentThread());
        LockSupport.park();
        LockSupport.park();
    }
}
