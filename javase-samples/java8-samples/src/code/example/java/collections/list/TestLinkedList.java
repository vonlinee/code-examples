package code.example.java.collections.list;

import java.util.Iterator;
import java.util.LinkedList;

public class TestLinkedList {
	public static void main(String[] args) {
		
		
		LinkedList<String> list = new LinkedList<>();
		
		list.add("A");
		list.add("B");
		list.add("C");
		list.add("D");
		list.add("E");
		
		list.offer("");
		
		
		Iterator<String> iterator = list.iterator();
		while (iterator.hasNext()) {
			String next = iterator.next();
			System.out.println(next);
		}
		
		
	}
}
