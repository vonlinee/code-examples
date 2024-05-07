package org.example.java8.primary.generic;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GenericClass<K, V> {

    K f1;
    V f2;
    List<K> list1;
    List<V> list2;
    Map<K, V> map;

    public V method1(K key) {
        return (V) key;
    }

    public <T> void method2() {
        T t1;
        K k1;
        V v1;
    }

    class InnerClass extends ArrayList<K> {

    }

    public static void main(String[] args) {
        GenericClass<String, Integer> obj = new GenericClass<>();
        Integer value = obj.method1("name");
        System.out.println(value);
    }
}


