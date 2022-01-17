package code.example.java.collections;

import java.util.LinkedList;

public class TestList {
	
	public static void main(String[] args) {
		LinkedList<Hero> linkedList = new LinkedList<>();
		linkedList.add(new Hero("A"));
		linkedList.addFirst(new Hero("B"));
		linkedList.addLast(new Hero("C"));
		CollectionPrinter.println(linkedList);
	}
}
