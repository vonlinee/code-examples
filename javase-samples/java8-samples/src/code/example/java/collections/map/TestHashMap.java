package code.example.java.collections.map;

import java.util.HashMap;

import code.example.java.collections.Hero;
import code.example.java.collections.U;

public class TestHashMap {
	public static void main(String[] args) {
		HashMap<String, Hero> hashMap = new HashMap<>();
		
		Hero h1 = hashMap.put("1", new Hero("A"));
		Hero h2 = hashMap.put("2", null);
		Hero h3 = hashMap.put("3", new Hero("B"));
		Hero h4 = hashMap.put("1", null);
		Hero h5 = hashMap.put("3", new Hero("C"));
		
		
		U.println(hashMap);
		
	}
}
