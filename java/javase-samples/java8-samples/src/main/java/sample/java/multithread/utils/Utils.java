package sample.java.multithread.utils;

import java.util.concurrent.TimeUnit;

/**
 * 睡眠当前线程
 */
public class Utils {

    public static void sleepSeconds(int seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void sleepMinutes(int minutes) {
        try {
            TimeUnit.MINUTES.sleep(minutes);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void println(Object obj) {
        System.out.printf("[%s] %s%n", Thread.currentThread().getName(), obj);
    }
}
