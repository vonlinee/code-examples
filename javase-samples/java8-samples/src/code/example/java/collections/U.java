package code.example.java.collections;

import java.util.Collection;
import java.util.Map;

/**
 * 工具类
 * @author vonline
 */
public class U {

    public static <T> void println(Collection<T> collection) {
    	System.out.println("共" + collection.size() + "个元素:");
        for (T t : collection) {
            System.out.println(t);
        }
    }

    public static <K, V> void println(Map<K, V> map) {
    	System.out.println("共" + map.size() + "个元素:");
        for (K key : map.keySet()) {
            System.out.println(key + "\t" + map.get(key));
        }
    }

    public static <T> void println(T[] array) {
    	System.out.println("共" + array.length + "个元素:");
        for (T t : array) {
            System.out.print(t + " ");
        }
        System.out.println();
    }

    public static void println(int[] array) {
    	System.out.println("共" + array.length + "个元素:");
        for (int t : array) {
            System.out.print(t + " ");
        }
        System.out.println();
    }
}
