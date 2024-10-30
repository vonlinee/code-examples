package org.example.java8.multithread;

public class CountDownLatchDemo {

    public static void main(String[] args) throws InterruptedException {
        Thread t = new Thread(() -> {
            while (true) {
                Thread.currentThread().interrupt();
                if (Thread.interrupted()) {
                    System.out.println("current thread is interrupted, prepare to exit");
                    break;
                }
            }
        });
        t.start();
        Thread.sleep(3000);
    }
}
