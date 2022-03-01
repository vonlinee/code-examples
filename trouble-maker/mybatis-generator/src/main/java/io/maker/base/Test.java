package io.maker.base;

import io.maker.base.collection.FixedMap;
import io.maker.base.rest.OptResult;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Test {
    public static void main(String[] args) throws IOException {

        new HashMap<>();

        HashMap<String, Object> fixedMap = new HashMap<>(1);
        fixedMap.put("A", "A");

        HashMap<String, Object> fixedMap1 = new HashMap<>(0, 1);
        fixedMap.put("A", "A");
        int i = 0;
    }

    static final int MAXIMUM_CAPACITY = 1 << 30;

    static int tableSizeFor(int cap) {
        int n = cap - 1;
        n |= n >>> 1;
        n |= n >>> 2;
        n |= n >>> 4;
        n |= n >>> 8;
        n |= n >>> 16;
        return (n < 0) ? 1 : (n >= MAXIMUM_CAPACITY) ? MAXIMUM_CAPACITY : n + 1;
    }
}
