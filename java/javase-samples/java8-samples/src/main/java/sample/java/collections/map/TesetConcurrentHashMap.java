package sample.java.collections.map;

import java.util.concurrent.ConcurrentHashMap;

public class TesetConcurrentHashMap {
	
	public static void main(String[] args) {
		
		ConcurrentHashMap<String, String> hashMap = new ConcurrentHashMap<String, String>();
		
		hashMap.put("1", "A");
		hashMap.put("1", "A");
		hashMap.put("1", "A");
		
	}
}
