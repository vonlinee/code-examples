package sample.java.collections;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * https://blog.csdn.net/striner/article/details/86375684
 */
public class TestFailFast {

	public static void main(String[] args) {
//		testFailFast();
		testHashTalbeFailFast();
//		testFailFastInConcurrency();
	}

	public static void testFailFast() {
		List<String> list = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			list.add(i + "");
		}
		Iterator<String> iterator = list.iterator();
		int i = 0;
		while (iterator.hasNext()) {
			if (i == 3) {
				list.remove(3);
			}
			System.out.println(iterator.next());
			i++;
		}
	}
	
	/**
	 * HashTable也是fail-fast的，在JDK1.8官方文档中有说到。如果HashTable在迭代器创建之后的任何时候结构被修改了，
	 * 除了通过迭代器自己的remove方法，迭代器会抛出一个ConcurrentModificationException。因此面对并发修改时，迭代器是快速失败的。
	 * 但是HashTable的Enumeration方式遍历集合元素的话，那就不是fail-fast的了，是fail-safe的
	 * 
	 * HashTable是线程安全的，不存在查询时修改的问题
	 */
	public static void testHashTalbeFailFast() {
		Map<String, String> map = new Hashtable<>();
		for (int i = 0; i < 10; i++) {
			map.put(i + "", i + "");
		}
		Iterator<Entry<String, String>> it = map.entrySet().iterator();
		int i = 0;
		while (it.hasNext()) {
			if (i == 3) {
				map.remove(3 + "");
			}
			Entry<String, String> entry = it.next();
			System.out.println("key= " + entry.getKey() + " and value= " + entry.getValue());
			i++;
		}
	}

	public static void testHashMapFailFast() {
		Map<String, String> map = new HashMap<>();
		for (int i = 0; i < 10; i++) {
			map.put(i + "", i + "");
		}
		Iterator<Entry<String, String>> it = map.entrySet().iterator();
		int i = 0;
		while (it.hasNext()) {
			if (i == 3) {
				map.remove(3 + "");
			}
			Entry<String, String> entry = it.next();
			System.out.println("key= " + entry.getKey() + " and value= " + entry.getValue());
			i++;
		}
	}

	public static List<String> list = new ArrayList<>();

	public static void testFailFastInConcurrency() {
		for (int i = 0; i < 10; i++) {
			list.add(i + "");
		}
		Thread thread1 = new Thread() {
			@Override
			public void run() {
				Iterator<String> iterator = list.iterator();
				while (iterator.hasNext()) {
					String s = iterator.next();
					System.out.println(this.getName() + ":" + s);
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				super.run();
			}
		};
		Thread thread2 = new Thread() {
			int i = 0;

			@Override
			public void run() {
				while (i < 10) {
					System.out.println("thread2:" + i);
					if (i == 2) {
						list.remove(i);
					}
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					i++;
				}
			}
		};
		thread1.setName("thread1");
		thread2.setName("thread2");
		thread1.start();
		thread2.start();
	}
	
	//避免fail-fast的方法：
	// 1.在单线程的遍历过程中，如果要进行remove操作，可以调用迭代器的remove方法而不是集合类的remove方法
	// 2.使用fail-safe机制，使用java并发包(java.util.concurrent)中的CopyOnWriterArrayList类来代替ArrayList，
	// 使用 ConcurrentHashMap来代替hashMap
}
