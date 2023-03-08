package sample.java8.multithread.lock;

import sun.awt.Mutex;

public class SynchronizedTest02 {
    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        SynchronizedTest02 test02 = new SynchronizedTest02();
        for (int i = 0; i < 100000000; i++) {
            test02.append("abc", "def");
        }
        System.out.println("Time=" + (System.currentTimeMillis() - start));
    }

    public void append(String str1, String str2) {
        StringBuffer sb = new StringBuffer();
        sb.append(str1).append(str2);

        Mutex mutex;
    }
}