package sample.java8.multithread;

import java.util.Random;

public class VolatileTest {

    static boolean flag;
    static volatile int vi = 0;
    static int nv = 0;

    public static void test1() {
        new Thread(() -> {
            while (!flag) {
                System.out.println("loop");
            }
            System.out.println("break");
        }).start();
        new Thread(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            // vi = 2;
            flag = true;
            System.out.println("set true");
        }).start();
    }

    public static void test2() {
        Random random = new Random();
        new Thread(() -> {
            while (true) {
                nv = random.nextInt();
                vi = nv;
            }
        }, "w-thread-1").start();

        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                while (true) {
                    System.out.println(nv == vi);
                }
            }, "w-thread-" + i).start();
        }
    }

    public static void test3() {
        vi = 1;
        nv = 2;
    }

    public static void main(String[] args) {
        test2();
    }
}
