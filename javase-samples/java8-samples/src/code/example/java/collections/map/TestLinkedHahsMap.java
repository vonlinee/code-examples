package code.example.java.collections.map;

import java.util.LinkedHashMap;

import code.example.java.collections.Hero;
import code.example.java.collections.U;

public class TestLinkedHahsMap {
    public static void main(String[] args) {
        LinkedHashMap<String, Hero> map = new LinkedHashMap<>();
        map.put("A", new Hero("1", "NAME1"));
        map.put("B", new Hero("2", "NAME2"));

        U.println(map);
    }
}
