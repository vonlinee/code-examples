package org.example.java8.collections.list;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TestArrayList {
    public static void main(String[] args) {

        test4();
       
    }
    
    public static void test1() {
    	ArrayList<String> list = new ArrayList<>();

        list.add("AAA");
        list.add("AAA");
        list.add("B");
        list.add("AAA");
        
        int indexOf = list.indexOf("AAA");
        String e3 = list.get(3);
        int i = Collections.binarySearch(list, "AAA");
        System.out.println(i);
        String toBeRemoved = list.remove(i);
        boolean result = list.remove("AAA");
    }
    
    public static <E> void test(List<E> list) {
    	List<E> syncList = Collections.synchronizedList(list);
    	
    }
    
    
    public static <E> void test3() {
    	List<String> list = Arrays.asList("1", "2", "3");
    	System.out.println(list.getClass()); // class java.util.Arrays$ArrayList
    	list.add("4"); // Exception in thread "main" java.lang.UnsupportedOperationException
    }
    
    public static void test4() {
    	List<String> list = new ArrayList<>();
    	list.add(0, "A");
    	list.add(1, "C"); // A, C
    	list.add(0, "B"); // 在同一个下标再加元素，则后面的元素向后移动一个位置  [B, A, C]
    	System.out.println(list);
    }
}
