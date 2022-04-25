package sample.java.multithread.concurrency.keyword;

import java.util.concurrent.TimeUnit;

public class TaskRunner {
    private static int number;
    private static boolean ready;

    public static void main(String[] args) throws InterruptedException {
        new Thread(() -> {
            while (!ready) {
                // ready == false
            }
            System.out.println(number);
        }).start();
        number = 42;
        ready = true;
    }
}