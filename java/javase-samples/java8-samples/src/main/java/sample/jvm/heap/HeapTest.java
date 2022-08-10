package sample.jvm.heap;

import java.util.concurrent.TimeUnit;

/**
 * -Xms10m -Xmx10m
 * @author vonline
 * @since 2022-07-24 4:46
 */
public class HeapTest {
    public static void main(String[] args) {
        try {
            TimeUnit.SECONDS.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
