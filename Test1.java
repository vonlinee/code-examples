package test;

public class Test1 {
	public static void main(String[] args) {
		test3();
		System.out.println(repeat('A', 2));
	}

	public static void test3() {
		and(hash("1"), 16);
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

	public static int hash(Object key) {
		int h;
		return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
	}
}
