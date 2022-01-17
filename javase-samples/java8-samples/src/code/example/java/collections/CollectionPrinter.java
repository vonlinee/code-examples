package code.example.java.collections;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

public class CollectionPrinter {

	public static <T> void println(Collection<T> collection) {
		Iterator<T> iterator = collection.iterator();
		while (iterator.hasNext()) {
			System.out.println(iterator.next());
		}
	}

	public static <K, V> void println(Map<K, V> map) {
		Iterator<K> iterator = map.keySet().iterator();
		while (iterator.hasNext()) {
			K key = iterator.next();
			System.out.println(key + ":" + map.get(key));
		}
	}
}
