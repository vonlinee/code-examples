package org.example.java8.multithread.concurrency;

public class WaitInSyncBlockTest {

    static final Object mutex = new Object();

    public static void main(String[] args) {

    }

    /**
     * @param a 字符串A，不为空
     * @param b 字符串B，不为空
     * @return 1: a > b, 0: a = b, -1: a < b
     */
    static int compare(String a, String b) {
        int max = Math.min(a.length(), b.length());
        for (int i = 0; i < max; i++) {

        }
        return 1;
    }
}