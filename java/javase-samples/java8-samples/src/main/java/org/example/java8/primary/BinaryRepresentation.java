package org.example.java8.primary;

public class BinaryRepresentation {
    public static void main(String[] args) {
        int num = 42; // 示例数字

        // 打印 int 类型的二进制表示
        String binaryString = Integer.toBinaryString(num);
        System.out.println("Integer " + num + " in binary: " + binaryString);

        // 打印 byte 类型的二进制表示
        byte b = 5;
        String byteBinary = String.format("%8s", Integer.toBinaryString(b & 0xFF)).replace(' ', '0');
        System.out.println("Byte " + b + " in binary: " + byteBinary);

        // 打印 short 类型的二进制表示
        short s = 100;
        String shortBinary = String.format("%16s", Integer.toBinaryString(s & 0xFFFF)).replace(' ', '0');
        System.out.println("Short " + s + " in binary: " + shortBinary);

        // 打印 long 类型的二进制表示
        long l = 123456789L;
        String longBinary = Long.toBinaryString(l);
        System.out.println("Long " + l + " in binary: " + longBinary);
    }
}