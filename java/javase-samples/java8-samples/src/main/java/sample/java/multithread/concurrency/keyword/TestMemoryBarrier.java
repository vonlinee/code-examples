package sample.java.multithread.concurrency.keyword;

public class TestMemoryBarrier {

    public static volatile int i = 0;

    public static synchronized void m() {
        // do nothing
    }

    public static void n() {
        i = 1;
    }

    public static void main(String[] args) {
        for (int j = 0; j < 1000000; j++) {
            m();
            n();
        }
    }
}
