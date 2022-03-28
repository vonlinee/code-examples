package io.pocket.base.func;

import io.pocket.base.lang.Value;

import java.util.HashMap;
import java.util.Map;

public class MethodParam {
    Map<String, Value> params = new HashMap<>();

    MethodParam() {
        Value value = new Value(10);

        int i = value.getInt();

        System.out.println(i);
    }

    public static void main(String[] args) {

    }
}
