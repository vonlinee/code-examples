package org.example.jvm.heap;

import java.util.ArrayList;

public class OOMTest {
    public static void main(String[] args) {
        int len = 10000;
        ArrayList<byte[][]> list = new ArrayList<>();
        while (true) {
            try {
                list.add(new byte[len][len]);
            } catch (OutOfMemoryError error) {
                // 可以被捕获
                error.printStackTrace();
                break;
            }
        }
    }
}