package org.example.java8.multithread.concurrency;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

public class WaitInSyncBlockTest {

    static final Object mutex = new Object();

    public static void main(String[] args) {
        try {
            mutex.wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        BiMap<String, Object> map = HashBiMap.create();
    }
}