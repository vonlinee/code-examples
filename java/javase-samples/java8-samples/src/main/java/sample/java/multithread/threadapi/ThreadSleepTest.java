package sample.java.multithread.threadapi;

import sample.java.multithread.Sleep;

public class ThreadSleepTest {

    private static final Mutex lock = new Mutex("mutex-lock-1");

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                synchronized (lock) {
                    System.out.println(Thread.currentThread().getName() + " holds the lock " + Thread.holdsLock(lock));
                }
                Sleep.seconds(10);
            }, "thread-" + i).start();
        }
    }
}
