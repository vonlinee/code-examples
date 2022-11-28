package io.devpl.auth;

import java.util.concurrent.locks.Lock;

public class Synchronizer {
    Lock lock = new Lock();

    public void doSynchronized() throws InterruptedException {
        this.lock.lock();
        // critical section
        this.lock.unlock();
    }
}