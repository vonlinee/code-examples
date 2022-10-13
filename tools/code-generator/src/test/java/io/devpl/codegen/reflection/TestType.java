package io.devpl.codegen.reflection;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class TestType {

    public static void main(String[] args) {

        Map<String, Integer> map = new HashMap<>();

        System.out.println(map.getClass().getTypeName());
        System.out.println(Arrays.toString(map.getClass().getTypeParameters()));

    }
}
