package org.example.jvm.gc;

/**
 * -Xms10m -Xmx10m
 * @author vonline
 * @since 2022-07-25 0:03
 */
public class StringTest {

    public static void main(String[] args) {
        test1();
    }

    public static void test1() {
        int times = 1000000000;
        String s = "NUMBER-";
        for (int i = 0; i < times; i++) {
            s = s + i;
            s.intern();
        }
        System.out.println(s.length());
    }
}
