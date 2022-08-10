package io.maker.base.utils;

public class Exceptions {


    public static void main(String[] args) {
        method();
    }

    public static int method() {
        try {
            return method1();
        } catch (Exception exception) {
            return 10;
        }
    }

    public static int method1() {
        return method2();
    }

    public static int method2() {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        for (StackTraceElement stackTraceElement : stackTrace) {
            System.out.println(stackTraceElement);
        }
        return 10;
    }


}
