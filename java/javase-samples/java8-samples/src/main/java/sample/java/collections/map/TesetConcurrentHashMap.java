package sample.java.collections.map;

import java.lang.reflect.Field;
import java.util.concurrent.ConcurrentHashMap;

public class TesetConcurrentHashMap {

	public static void main(String[] args)
			throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {

		ConcurrentHashMap<String, String> hashMap = new ConcurrentHashMap<>();
		hashMap.put("1", "A");
		hashMap.put("1", "A");
		hashMap.put("1", "A");

		Field counterCellsField = ConcurrentHashMap.class.getDeclaredField("counterCells");
		counterCellsField.setAccessible(true);
		Object[] counterCells = (Object[]) counterCellsField.get(hashMap);
		for (int i = 0; i < 100; i++) {
			hashMap.put("" + i, "" + i);
			if (counterCells != null) {
				System.out.println(counterCells.length);
			}
		}
		
		System.out.println(hashMap.size());
	}
}
