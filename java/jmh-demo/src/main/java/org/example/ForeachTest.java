package org.example;

import java.util.ArrayList;
import java.util.List;

public class ForeachTest {

    final int count = 100 * 1000 * 1000;

    public void inner() {
        List<Object> list = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            Object obj = new Object();
            list.add(obj);
        }
    }

    public void outer() {
        List<Object> list = new ArrayList<>();
        Object obj;
        for (int i = 0; i < count; i++) {
            obj = new Object();
            list.add(obj);
        }
    }

    public static void main(String[] args) {
        ForeachTest foreach = new ForeachTest();
        long start = System.currentTimeMillis();
        foreach.outer();
        System.out.println("outer cost " + (System.currentTimeMillis() - start) + " ms");
        start = System.currentTimeMillis();
        foreach.inner();
        System.out.println("inner cost " + (System.currentTimeMillis() - start) + " ms");
    }
}