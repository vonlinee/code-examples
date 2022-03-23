package sample.java.collections.map;

import java.util.HashMap;

import sample.java.collections.Hero;
import sample.java.collections.U;


public class TestHashMap {
	public static void main(String[] args) {
//		test2();
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

	static int hash(Object key) {
	    int h;
	    return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
	}
}
