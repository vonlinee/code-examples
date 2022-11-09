package sample.java.multithread.falsingshare;

import org.openjdk.jol.info.ClassLayout;
import sun.misc.Contended;

public class FalseSharingExample {
    public static void main(String[] args) {
        Counter counter1 = new Counter();
        Counter counter2 = new Counter();
        long iterations = 1_000_000_000;
        new Thread(() -> {
            long startTime = System.currentTimeMillis();
            for (long i = 0; i < iterations; i++) {
                counter1.count1++;
            }
            long endTime = System.currentTimeMillis();
            System.out.println("total time: " + (endTime - startTime) + " ms");
        }).start();
        new Thread(() -> {
            long startTime = System.currentTimeMillis();
            for (long i = 0; i < iterations; i++) {
                counter2.count2++;
            }
            long endTime = System.currentTimeMillis();
            System.out.println("total time: " + (endTime - startTime) + " ms");
        }).start();

    }


}

class Counter {
    // sun.misc.Contended;
    @Contended(value = "group1")
    public volatile long count1 = 0;
    public volatile long count2 = 0;
}
