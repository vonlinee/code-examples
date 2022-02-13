package code.example.java.collections;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Map;
import java.util.Random;

/**
 * 工具类
 * @author vonline
 */
public class U {

	public static final Object LOCK = new Object();
	
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
    
    public static <T> void println(T bean) {
    	if (bean == null) {
			System.out.println("null");
		}
    	Class<? extends Object> clazz = bean.getClass();
    	Field[] fields = clazz.getDeclaredFields();
    	for(Field field : fields) {
    		field.setAccessible(true);
    		try {
				System.out.println(field.getName() + "\t" +  field.getType().getName() + "\t" + field.get(bean));
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
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
    
	public static void and(int i, int j) {
		String iStr = Integer.toBinaryString(i);
		String jStr = Integer.toBinaryString(j);
		int m = iStr.length() - jStr.length();
	}

	public static String leftFillToLength(final String src, char c, int len) {
		len = len - src.length();
		return len > 0 ? repeat(c, len) + src : src;
	}
	
	public static String rightFillToLength(final String src, char c, int len) {
		len = len - src.length();
		return len > 0 ? src + repeat(c, len) : src;
	}
	
	/**
	 * 按指定次数重复某个字符形成字符串
	 * @param c
	 * @param count
	 * @return
	 */
	public static String repeat(char c, int count) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < count; i++) {
			sb.append(c);
		}
		return sb.toString();
	}

	/**
	 * 计算HashCode
	 * @param key
	 * @return
	 */
	public static int hash(Object key) {
		int h;
		return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
	}
	
	public static String right(String binaryString, int i) {
		return Long.toBinaryString(binaryStringToLong("11010111011100001") >>> i);
	}

	/**
	 * 二进制字符串转为long型数据
	 * @param binaryString
	 * @return
	 */
	public static long binaryStringToLong(String binaryString) {
		return Long.parseLong(binaryString, 2);
	}

	/**
	 * 随机长度的二进制字符串
	 * @param len
	 * @return
	 */
	public static String randomBinaryString(int len) {
		StringBuilder s = new StringBuilder();
		Random random = new Random();
		for (int i = 0; i < len; i++) {
			s.append(random.nextInt(2));
		}
		return s.toString();
	}
}
