package sample.java8.multithread.primary;

/**
 * @author vonline
 * @since 2022-08-29 22:12
 */
public class WaitNotifyTest1 {

    public static void main(String[] args) throws InterruptedException {
        Object lock = new Object();
        synchronized (lock) {
            lock.wait();
        }

    }
}
