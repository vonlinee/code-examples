package samples.meta;

import java.util.Date;
import java.util.HashMap;


public class TestClass<K extends Date, V> extends HashMap<K, V> {

	private static final long serialVersionUID = 1L;

	private int age;
	
	private String name;
	
	private final long count = 0;
	
	public int method1() {
		return 1;
	}
	
	@SuppressWarnings("unchecked")
	public <T> T method2(K key, String value) {
		return (T) key;
	}
}