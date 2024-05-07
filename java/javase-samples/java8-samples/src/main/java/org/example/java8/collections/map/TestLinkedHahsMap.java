package org.example.java8.collections.map;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import org.example.java8.collections.Hero;
import org.example.java8.collections.U;

public class TestLinkedHahsMap {
    public static void main(String[] args) {
        test2();
    }

    public static void test1() {
        LinkedHashMap<String, Hero> map = new LinkedHashMap<>();
        map.put("A", new Hero("1", "NAME1"));
        map.put("B", new Hero("2", "NAME2"));
        U.println(map);
    }

    public static void test2() {
        testLinkedHashMap();
        testLRULinkedHashMap();
    }

    public static void testLinkedHashMap() {
        //容量固定，accessOrder=true
        Map<Integer, Integer> map = new LinkedHashMap<Integer, Integer>(5, 0.75f, true);
        map.put(1, 1);
        map.put(2, 2);
        map.put(3, 3);
        map.put(4, 4);
        map.put(5, 5);
        //此时输出1,2,3,4,5
        for (Iterator<Map.Entry<Integer, Integer>> it = map.entrySet().iterator(); it.hasNext(); ) {
            System.out.println(it.next().getValue());
        }
        map.put(4, 4);
        map.put(6, 6);

        //此时输出1,2,3,5,4,6（自动扩容）
        for (Iterator<Map.Entry<Integer, Integer>> it = map.entrySet().iterator(); it.hasNext(); ) {
            System.out.println(it.next().getValue());
        }
    }

    public static void testLRULinkedHashMap() {
        //容量固定，accessOrder=true
        Map<Integer, Integer> map = new LRULinkedHashMap<Integer, Integer>(5);
        map.put(1, 1);
        map.put(2, 2);
        map.put(3, 3);
        map.put(4, 4);
        map.put(5, 5);

        //此时输出1,2,3,4,5
        for (Iterator<Map.Entry<Integer, Integer>> it = map.entrySet().iterator(); it.hasNext(); ) {
            System.out.println(it.next().getValue());
        }
        map.put(4, 4);
        map.put(6, 6);

        //此时输出2,3,5,4,6（容量锁定，进行删除）
        for (Iterator<Map.Entry<Integer, Integer>> it = map.entrySet().iterator(); it.hasNext(); ) {
            System.out.println(it.next().getValue());
        }

    }
}

class LRULinkedHashMap<K, V> extends LinkedHashMap<K, V> {

    /**
     *
     */
    private static final long serialVersionUID = 1882839504956564761L;

    private int capacity;

    public LRULinkedHashMap(int capacity) {
        super(capacity, 0.75f, true);
        this.capacity = capacity;
    }

    @Override
    public boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        System.out.println("根据LRU，删除最近最少使用元素[K=" + eldest.getKey() + " V=" + eldest.getValue() + "]");
        //此行代码是关键，一旦容量超出限制，即按照LRU进行删除
        return size() > capacity;
    }
}
