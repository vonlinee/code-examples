package io.maker.base;

import io.maker.base.collection.FixedMap;
import io.maker.base.rest.OptResult;

import java.io.IOException;
import java.util.ArrayList;

public class Test {
    public static void main(String[] args) throws IOException {

        FixedMap<String, Object> fixedMap = new FixedMap<>(3);
        fixedMap.put("A", "A");
        fixedMap.put("B", "A");
        fixedMap.put("C", "A");
        fixedMap.put("E", "A");
    }
}
