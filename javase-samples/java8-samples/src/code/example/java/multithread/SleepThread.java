package code.example.java.multithread;

import java.util.concurrent.TimeUnit;

/**
 * 睡眠当前线程
 */
public class SleepThread {

    public static void seconds(int seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void minutes(int minutes) {
        try {
            TimeUnit.MINUTES.sleep(minutes);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
