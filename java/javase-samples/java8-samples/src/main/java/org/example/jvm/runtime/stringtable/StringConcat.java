package org.example.jvm.runtime.stringtable;

import org.junit.jupiter.api.Test;

public class StringConcat {

    @Test
    public void test1() {
        String s1 = "a";
        String s2 = "b";
        String s3 = "ab";
        String s4 = "a" + "b";
        String s5 = s1 + s2;
        String s6 = "a" + s2;
        String s7 = s1 + s2;

    }

    @Test
    public void test2() {
        String s1 = "a";
        String s2 = "b";
        String s3 = "ab";
        String s4 = (s1 + s2).intern();
        System.out.println(s3 == s4);
    }

    @Test
    public void test4() {
        long start1 = System.currentTimeMillis();
        String s1 = "";
        for (int i = 0; i < 100000; i++) {
            s1 += 'a';
        }
        long end1 = System.currentTimeMillis();

        long start2 = System.currentTimeMillis();
        StringBuilder s2 = new StringBuilder();
        for (int i = 0; i < 1000000; i++) {
            s2.append('a');
        }
        long end2 = System.currentTimeMillis();
        System.out.println((end2 - start2) + "毫秒 " + (end1 - start1) + "毫秒");
    }

    @Test
    public void test3() {
        String s1 = new String("ab");

        s1.toString();
    }

    @Test
    public void test5() {
        String s = new String("1");
        s.intern(); // 调用此方法之前，字符串常量吃已经存在"1"
    }
}
