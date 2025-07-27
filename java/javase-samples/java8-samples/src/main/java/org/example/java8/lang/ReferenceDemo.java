package org.example.java8.lang;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

public class ReferenceDemo {

    static WeakReference<Map<String, Object>> reference = new WeakReference<>(new HashMap<>());

    public static void main(String[] args) throws InterruptedException {
        Map<String, Object> data = reference.get();

        reference.clear();

        boolean enqueue = reference.enqueue();
        if (enqueue) {
            System.out.println(true);
        }

        System.out.println(data);

        Thread.sleep(400000);
    }
}
