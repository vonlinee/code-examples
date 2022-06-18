package algorithm;

public class TestBitOperation {


    public static void main(String[] args) {

        print(33);

    }

    /**
     * 将整形的二进制打印出来
     *
     * @param num 整形是32位无符号数， long是64位的
     */
    public static void print(int num) {
        String s = Integer.toBinaryString(num);
        System.out.println(s);
    }
}
