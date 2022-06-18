package leetcode.algorithm;

public class TestBitOperation {

    public static void main(String[] args) {
//        print(4);
        
//        printBinaryString(-1);
        
//        print(1024);
//        print(1024 >> 1);
//        print(1024 >>> 1);
//        
//        print(Integer.MIN_VALUE);
//        print(Integer.MIN_VALUE >> 1);
//        print(Integer.MIN_VALUE >>> 1);
        
        
    	
        int c = Integer.MIN_VALUE;
        int d = -c;
        d = ~c + 1; // 相反数的不同表示，不管正数还是负数，取相反数都是取反加一
        print(c);
        print(d);
        // 这也是使用补码的好处之一：对于-（取相反数）这个运算符，只需要走一套逻辑
        
    }

    /**
     * 将整形的二进制打印出来
     * @param num 整形是32位无符号数， long是64位的
     */
    public static void print(int num) {
    	System.out.print(num + "\t");
    	for (int i = 31; i > 0; i--) {
			System.out.print((num & (1 << i)) == 0 ? "0" : "1");
		}
    	System.out.println();
    }
    
    public static void shift() {
    	print(Integer.MAX_VALUE);
    }
    
    public static void printBinaryString(int num) {
    	System.out.println(Integer.toBinaryString(num));
    }
}
