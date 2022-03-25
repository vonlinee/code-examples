package sample.java.collections.map;

import java.util.TreeMap;

import sample.java.collections.set.Hero;

public class TestTreeMap {
    public static void main(String[] args) {
        TreeMap<String, Hero> map = new TreeMap<>();
        
        map.put("1", new Hero(1, ""));
    }
}
