package sample.java.primary.operator;

import java.util.Random;

import org.junit.jupiter.api.Test;

/**
 * 位操作符
 * https://cloud.tencent.com/developer/article/1338265
 * @author vonline
 */
public class TestBitOperator {

	// 0正，1负
	
    private static final int COUNT_BITS = Integer.SIZE - 3; //29
    private static final int CAPACITY   = (1 << COUNT_BITS) - 1;
    // runState is stored in the high-order bits
    private static final int RUNNING    = -1 << COUNT_BITS;
    private static final int SHUTDOWN   =  0 << COUNT_BITS;
    private static final int STOP       =  1 << COUNT_BITS;
    private static final int TIDYING    =  2 << COUNT_BITS;
    private static final int TERMINATED =  3 << COUNT_BITS;

	public static void main(String[] args) {
		test1();
	}
	
	public static void test7() {
		println(Integer.toBinaryString(COUNT_BITS)); //11101
		int k = 1 << COUNT_BITS;
		System.out.println(k);
		int i = 10;
		int j = i & ~CAPACITY;
		println(Integer.toBinaryString(j)); //11101
	}
	
	public static void test5() {
		byte b = -128;
		System.out.println(Integer.toBinaryString(b)); //11111111111111111111111110000000
		byte c = 125;
		System.out.println(Integer.toBinaryString(b)); //11111111111111111111111110000000
		int i = b >> 2; //带符号右移
		System.out.println(Integer.toBinaryString(i)); //11111111111111111111111111100000
		int j = b >>> 2; //不带符号右移，左边补0
		System.out.println(Integer.toBinaryString(j)); //111111111111111111111111100000,省略了前面的00
	}
	
	public static void test4() {
		byte b = 127;
		System.out.println(Integer.toBinaryString(b)); //1111111 ,结果只有7位
		byte a = ~127;  //~127取反二进制为：10000000
		System.out.println(a); //-128
		//JAVA在对不足32位的数(byte,char,short)进行移位运算时候,都会先转成int(32位)
		//因此:a = 11111111111111111111111110000000
		System.out.println(Integer.toBinaryString(a)); //11111111111111111111111110000000 ,结果只有32位
	}
	
	public static void test1() {
		System.out.println(Integer.toBinaryString(127));//1111111
		byte a = ~127;  //二进制为：10000000 = -128
		//JAVA在对不足32位的数(byte,char,short)进行移位运算时候,都会先转成int(32位)，因此 a >> 1返回int
		// byte b = a >> 1; // Type mismatch: cannot convert from int to byte
		
		//这里toBinaryString接收int类型，因此:a = 11111111111111111111111110000000
		System.out.println(Integer.toBinaryString(a));  //11111111111111111111111110000000
		//0-正数，1-负数
		System.out.println(a); //-128
		int i = a >>> 2; //无符号右移2位，左边补0
		System.out.println("i=" + i); //1073741792
		System.out.println(Integer.toBinaryString(i)); //00111111111111111111111111100000
		a = (byte) (i); //强制转成byte型,将对结果进行截断
		System.out.println(a);  //因此a = 11100000 = -32，第1个为符号位
		System.out.println(Integer.toBinaryString(Byte.toUnsignedInt(a))); //11100000
		System.out.println(Integer.toBinaryString(a)); //11111111111111111111111111100000
	}
	
	public static void test8() {
		
	}

	//Integer.toBinaryString(a)函数的结果是长度为32的字符串
	public static void test3() {
		System.out.println(Integer.toBinaryString(12)); //1100
		System.out.println(Integer.toBinaryString((byte)12)); //1100
	}
	
	@Test
	public void test6() {
		// 32/64位架构
		println(1 >> 32);
		println(-1 >> 31); // -1
		println(-1 >> 32); // -1
		System.out.println(32 & 0x1f); // 0
	}
	
	public static void test2() {
//		按位运算符，>>表示算数右移，>>>表示逻辑右移
		System.out.println(128 << 16 >>> 16);
		System.out.println(128 >>> 16);
		System.out.println(128 >> 16);

		System.out.println(Long.MAX_VALUE);
		String l1 = Long.toUnsignedString(Long.MAX_VALUE);
		String l = Long.toBinaryString(Long.MAX_VALUE);

		System.out.println(l1);
		System.out.println(l.length()); // 63
		System.out.println(Long.toBinaryString(Long.MAX_VALUE >>> 16));

		System.out.println(right("11010111011100001", 16));
		System.out.println(randomBinaryString(17));
		System.out.println("=========================");
		System.out.println(Integer.parseInt("11001101", 2)); // 205
		System.out.println(Integer.toBinaryString(205 >> 1));
		System.out.println(Integer.toBinaryString(205 >>> 1));
		System.out.println(Integer.parseInt("1011001101", 2)); // 205
		System.out.println(Integer.toBinaryString(205 >> 1));
		System.out.println(Integer.toBinaryString(205 >>> 1));
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
	
	public static void println(Object obj) {
		if (obj == null) {
			System.out.println("object is null");
			return;
		}
		System.out.println(obj + " [" + obj.getClass().getName() + "]");
	}
}
