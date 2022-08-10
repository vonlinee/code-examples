package sample.java.multithread;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author vonline
 * @since 2022-07-28 6:43
 */
public class Test1 {

    private static volatile int times = 0;
    private static AtomicInteger count = new AtomicInteger(0);
    private static final Lock lock = new ReentrantLock();

    public static void main(String[] args) {
        new Thread(() -> {
            while (true) {
                if (count.incrementAndGet() == 1) {
                    doTask(false);
                }
                if (times >= 6) break;
            }
        }, "A").start();
        new Thread(() -> {
            while (true) {
                if (count.incrementAndGet() == 1) {
                    doTask(false);
                }
                if (times >= 6) break;
            }
        }, "B").start();
        new Thread(() -> {
            while (true) {
                if (count.incrementAndGet() == 1) {
                    doTask(false);
                }
                if (times >= 6) break;
            }
        }, "C").start();
        new Thread(() -> {
            while (true) {
                if (count.incrementAndGet() == 1) {
                    doTask(true);
                }
                if (times >= 6) break;
            }
        }, "D").start();
    }

    private static void doTask(boolean update) {
        try {
            lock.lock();
            System.out.print(Thread.currentThread().getName() + times + " ");
            if (update) times++;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}
