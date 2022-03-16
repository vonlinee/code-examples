package sample.java.collections.list;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TestArrayList {
    public static void main(String[] args) {
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
    
    
    
    
}
