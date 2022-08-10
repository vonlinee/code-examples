package sample.redis.jedis.lock;

import java.util.concurrent.TimeUnit;

/**
 * @author vonline
 * @since 2022-07-29 18:28
 */
public class Utils {

    public static void sleepThreadSeconds(int seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
