package sample.java.multithread;

/**
 * @author vonline
 * @since 2022-07-28 18:10
 */
public class VolatileNoAtomic {

    volatile int num = 0;

    public void numberAdd() {
        num++;
    }

    public static void main(String[] args) {
        VolatileNoAtomic main = new VolatileNoAtomic();
        for (int i = 0; i < 50; i++) {
            new Thread(() -> {
                for (int j = 0; j < 1000000; j++) {
                    main.numberAdd();
                }
            }, "Thread-" + i).start();
        }
        while (Thread.activeCount() > 2) {
            Thread.yield();
        }
        // 50 * 100 0000 = 5000 0000
        System.out.println("最终 num=" + main.num); // 最终 num=8933450
    }
}
