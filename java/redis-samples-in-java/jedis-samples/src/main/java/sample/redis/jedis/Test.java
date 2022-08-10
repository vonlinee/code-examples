package sample.redis.jedis;

import sample.redis.jedis.lock.RedisLock;
import sample.redis.jedis.lock.RedisLockImpl;
import sample.redis.jedis.lock.Utils;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author vonline
 * @since 2022-07-29 18:01
 */
public class Test {

    static volatile AtomicInteger times = new AtomicInteger(0);

    public static void main(String[] args) {
        RedisLock lock = new RedisLockImpl();
        Runnable task = () -> {
            while (times.get() <= 100) {
                lock.lock("lock.foo");
                times.incrementAndGet();
                Utils.sleepThreadSeconds(4);
                lock.unlock("lock.foo");
            }
        };
        new Thread(task, "C1").start();
        new Thread(task, "C2").start();
        new Thread(task, "C3").start();
    }
}
