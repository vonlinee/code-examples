package org.example.java8.collections;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * fail-safe:这种遍历基于容器的一个克隆。因此，对容器内容的修改不影响遍历。
 * java.util.concurrent包下的容器都是安全失败的,可以在多线程下并发使用,并发修改。
 * 常见的的使用fail-safe方式遍历的容器有ConcerrentHashMap和CopyOnWriteArrayList等。
 * 
 * 
 * 采用安全失败机制的集合容器，在遍历时不是直接在集合内容上访问的，而是先复制原有集合内容，在拷贝的集合上进行遍历。
 * 由于迭代时是对原集合的拷贝进行遍历，所以在遍历过程中对原集合所作的修改并不能被迭代器检测到，所以不会触发Concurrent Modification Exception
 * 
 * 基于拷贝内容的优点是避免了Concurrent Modification Exception，但同样地，迭代器并不能访问到修改后的内容，
 * 即：迭代器遍历的是开始遍历那一刻拿到的集合拷贝，在遍历期间原集合发生的修改迭代器是不知道的
 */
public class TestFailSafe {
	
	private static List<String> list = new CopyOnWriteArrayList<>();
	
	public static void main(String[] args) {
		testModifyByIterator();
	}
	
	public static void testModifyByIterator() {
		List<String> list = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			list.add(i + "");
		}
		Iterator<String> iterator = list.iterator();
		int i = 0;
		while (iterator.hasNext()) {
			if (i == 3) {
				iterator.remove();
			}
			System.out.println(iterator.next());
			i++;
		}
		
		System.out.println("删除后的元素 => " + list);
	}
	
	public static void testThreadSafeCollection() {
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
}
