package org.example.java8.multithread.concurrency;

/**
 * https://stackoverflow.com/questions/106591/what-is-the-volatile-keyword-useful-for
 * http://web.archive.org/web/20210221170926/https://www.ibm.com/developerworks/java/library/j-jtp06197/
 */
public class TestVolatile {

    static int i = 0;

    public static void main(String[] args) throws InterruptedException {
        for (int j = 0; j < 10; j++) {
            new Thread(() -> {
                for (int k = 0; k < 10; k++) {
                    i++;
                    System.out.println(Thread.currentThread().getName() + " " + i);
                }
            }).start();
        }
        Thread.sleep(3000);
        System.out.println(i);
    }
}
