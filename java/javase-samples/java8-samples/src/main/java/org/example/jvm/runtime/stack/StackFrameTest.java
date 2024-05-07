package org.example.jvm.runtime.stack;

public class StackFrameTest {

    public static void main(String[] args) {
        StackFrameTest test = new StackFrameTest();
        System.out.println(test.method1());
    }

    public double method1() {
        System.out.println("方法1开始运行");
        int i = method2();
        System.out.println("方法1结束运行");
        return i + 1;
    }

    public int method2() {
        System.out.println("方法2开始运行");
        int r = 10;
        System.out.println("方法2结束运行");
        return r;
    }
}
