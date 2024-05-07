package org.example.jvm.gc;

public class SystemGCTest {
    private static final int len = 1000;

    private final long[][] data = new long[len][len];

    private static boolean flag = false;

    public static void main(String[] args) {
        int times = 10000;
        for (int i = 0; i < times; i++) {
            if (!flag) {
                new SystemGCTest();
            }
        }
        System.gc(); // 提醒JVM的垃圾回收器执行gc，但是不确定是否马上执行gc
        // System.runFinalization();//强制执行使用引用的对象的finalize()方法
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        System.out.println(Thread.currentThread().getName() + " " + this + " => finalize");
        flag = true;
    }
}