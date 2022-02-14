package code.example.java.collections.map;

import java.util.TreeMap;

import code.example.java.collections.set.E;

public class TestTreeMap {
    public static void main(String[] args) {
        TreeMap<String, E> map = new TreeMap<>();
        
        map.put("1", new E(1));
    }
}
