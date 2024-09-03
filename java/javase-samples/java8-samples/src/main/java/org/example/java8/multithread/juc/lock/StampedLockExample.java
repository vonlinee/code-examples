package org.example.java8.multithread.juc.lock;

import java.util.*;
import java.util.concurrent.locks.StampedLock;

public class StampedLockExample {

    Map<String, Object> map = Collections.synchronizedMap(new HashMap<>());
    List<String> list = Collections.synchronizedList(new ArrayList<>());

    public static void main(String[] args) {

        StampedLock lock = new StampedLock();

    }
}
