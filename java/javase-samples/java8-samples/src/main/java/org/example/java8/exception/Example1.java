package org.example.java8.exception;

import java.io.IOException;

public class Example1 {
    public static void main(String[] args) {
        try {
            f1();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    static void f1() {
        try {
            f2();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    static void f2() throws IOException {
        try {
            f3();
        } catch (Exception exception) {
            throw new IOException(exception);
        }
    }

    static void f3() {
        try {
            f4();
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    static void f4() {
        int i = 1 / 0;
    }
}