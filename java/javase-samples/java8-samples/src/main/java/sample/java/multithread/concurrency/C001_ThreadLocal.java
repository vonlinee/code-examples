package sample.java.multithread.concurrency;

import java.util.stream.IntStream;

public class C001_ThreadLocal {

    public static ThreadLocal<String> localVar = new ThreadLocal<>();

    public static void main(String[] args) {
        test1();
        
        ThreadLocal.withInitial(null);
        
        int i = ThreadLocal.class.getConstructors().length;
        System.out.println(i);
        
        ThreadLocal.withInitial(() -> 10);
        
    }

    public static void test1() {
        IntStream.range(0, 4).forEach(value -> {
            new Thread(() -> {
                localVar.set("" + value);
                System.out.println(Thread.currentThread().getName() + "->" + localVar.get());
            }).start();
        });
    }
}
