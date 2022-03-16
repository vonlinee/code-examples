package sample.java.collections.set;

import java.util.HashSet;
import java.util.LinkedHashSet;

public class TestHashSet {
	public static void main(String[] args) {
		
		HashSet<String> hashSet = new HashSet<>();
		boolean b1 = hashSet.add("A");
		
	}
	
	public static void test1() {
		HashSet<E> hashSet = new HashSet<>();
		boolean b1 = hashSet.add(new E(1));
		
		hashSet.stream().distinct();		
		
	}
	
	public static void test2() {
		
		LinkedHashSet<E> linkedHashSet = new LinkedHashSet<>();
		
		boolean b1 = linkedHashSet.add(new E(1));
		
	}
}
