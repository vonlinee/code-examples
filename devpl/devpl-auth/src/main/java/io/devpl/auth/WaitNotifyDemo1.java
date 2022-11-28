package io.devpl.auth;

public class WaitNotifyDemo1 {

    final Object monitor = new Object();
    boolean wasSignalled = false;

    public void doWait() {
        synchronized (monitor) {
            while (!wasSignalled) {
                try {
                    monitor.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            // clear signal and continue running.
            wasSignalled = false;
        }
    }

    public void doNotify() {
        synchronized (monitor) {
            wasSignalled = true;
            monitor.notify();
        }
    }
}