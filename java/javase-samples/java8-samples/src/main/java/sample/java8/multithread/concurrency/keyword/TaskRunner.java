package sample.java8.multithread.concurrency.keyword;

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