package org.example.java8.multithread.concurrency;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ConcurrentHashMap;

public class TestConcurrentHashMap {
    ConcurrentHashMap<String, Object> chashmap = new ConcurrentHashMap<>();

    @Test
    public void test1() {
        chashmap.put("name", "Zs");
    }
}
