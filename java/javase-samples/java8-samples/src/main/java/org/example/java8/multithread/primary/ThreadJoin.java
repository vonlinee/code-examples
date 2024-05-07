package org.example.java8.multithread.primary;

/**
 * @author vonline
 * @since 2022-08-25 22:53
 */
public class ThreadJoin {

    public static void main(String[] args) {
        String threadName = Thread.currentThread().getName();
        System.out.println(threadName + " start.");
        Thread bt = new Thread(() -> {
            System.out.println(Thread.currentThread().getName());
        });
        Thread at = new Thread(bt);
        try {
            bt.start();
            Thread.sleep(2000);
            at.start();
           at.join(); //在此处注释掉对join()的调用
        } catch (Exception e) {
            System.out.println("Exception from main");
        }
        System.out.println(threadName + " end!");
    }

}
