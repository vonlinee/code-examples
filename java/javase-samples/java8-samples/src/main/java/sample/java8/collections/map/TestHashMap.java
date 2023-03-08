package sample.java8.collections.map;

import org.junit.jupiter.api.Test;
import sample.java8.collections.Hero;
import sample.java8.collections.U;

import java.util.HashMap;


public class TestHashMap {
	public static void main(String[] args) {
		test3();
	}
	
	public static void test1() {
		HashMap<String, Hero> hashMap = new HashMap<>();
		Hero h1 = hashMap.put("1", new Hero("A"));
		Hero h2 = hashMap.put("2", null);
		Hero h3 = hashMap.put("3", new Hero("B"));
		Hero h4 = hashMap.put("1", null);
		Hero h5 = hashMap.put("3", new Hero("C"));
		U.println(hashMap);
		System.out.println("1232".hashCode());

	}
	
	public static void test2() {
		HashMap<String, Hero> hashMap = new HashMap<>(19);
		hashMap.put("1", new Hero("A"));
	}

	public static void test3() {
		System.out.println(hash("A"));
		System.out.println(hash("B"));
		System.out.println(hash("C"));
	}
	
	public static void test4() {
//		HashMap<String, String> hashMap = new HashMap<>(19);
//		
//		hashMap.put("1", "AAA");
//		
//		HashMap<String, String> hashMap1 = new HashMap<>();

		int hash = hash("5");
		System.out.println(hash);
		System.out.println(Integer.toBinaryString(hash));
		System.out.println(hash & 15);
		System.out.println(Integer.toBinaryString(16));
		
		String cls = System.getProperty("java.system.class.loader");
		System.out.println(cls);
	}
	
	@Test
	public void test5() {
		int capacity = 32; // 比32小的，&32 -> 0
		System.out.println(Integer.toBinaryString(capacity));
		System.out.println(31 & capacity);
		System.out.println(32 & capacity);
		System.out.println(33 & capacity);
	}
	
	
	/**
	 * hash
	 * @param key
	 * @return
	 */
	public static int hash(Object key) {
	    // int h;
	    // return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
		int h = key.hashCode();
		if (key == null) {
			return 0;
		} else {
			System.out.println(h >>> 16);
			return h ^ (h >>> 16);
		}
	}
}
