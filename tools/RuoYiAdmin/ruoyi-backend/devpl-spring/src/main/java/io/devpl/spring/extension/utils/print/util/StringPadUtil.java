package io.devpl.spring.extension.utils.print.util;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class StringPadUtil {

    public static String leftPad(String str, int size, char c) {
        if (str == null) {
            return null;
        }
        int strLength = strLength(str);
        if (size <= 0 || size <= strLength) {
            return str;
        }
        return repeat(size - strLength, c).concat(str);
    }

    public static String rightPad(String str, int size, char c) {
        if (str == null) {
            return null;
        }

        int strLength = strLength(str);
        if (size <= 0 || size <= strLength) {
            return str;
        }
        return str.concat(repeat(size - strLength, c));
    }

    public static String center(String str, int size, char c) {
        if (str == null) {
            return null;
        }

        int strLength = strLength(str);
        if (size <= 0 || size <= strLength) {
            return str;
        }
        str = leftPad(str, strLength + (size - strLength) / 2, c);
        str = rightPad(str, size, c);
        return str;
    }

    public static String leftPad(String str, int size) {
        return leftPad(str, size, ' ');
    }

    public static String rightPad(String str, int size) {
        return rightPad(str, size, ' ');
    }

    public static String center(String str, int size) {
        return center(str, size, ' ');
    }

    private static String repeat(int size, char c) {
        StringBuilder s = new StringBuilder();
        for (int index = 0; index < size; index++) {
            s.append(c);
        }
        return s.toString();
    }

    public static int strLength(String str) {
        return lengthOf(str, StandardCharsets.UTF_8);
    }

    /**
     * 不同的字符依靠二进制位不同来进行区分
     * 1字符=2字节，1字节=8位，英文和数字占一个字节，中文占一个字符，也就是两个字节
     * 在计算的字符串长度的时候，若有汉字或其他文字，直接用String.length()方法没法计算出准确的长度。
     * 比如墘字两个方法计算的长度不一样
     * <p>
     * 1.str.length() 返回此字符串的长度,长度等于字符串中 Unicode 代码单元的数量
     * 2.str.getBytes().length)
     *
     * <p>
     * 이것은매우긴번체의중국어이다
     * @param str     字符串
     * @param charset 编码
     * @return 字符串长度
     */
    public static int lengthOf(String str, Charset charset) {
        if (str.length() == 0) return 0;
        int len = 0;
        int j = 0;
        byte[] bytes = str.getBytes(charset);
        short tmpbit;
        do {
            tmpbit = (short) (bytes[j] & 0xF0);
            if (tmpbit >= 0xB0) {
                if (tmpbit < 0xC0) {
                    j += 2;
                    len += 2;
                } else if ((tmpbit == 0xC0) || (tmpbit == 0xD0)) {
                    j += 2;
                    len += 2;
                } else if (tmpbit == 0xE0) {
                    j += 3;
                    len += 2;
                } else if (tmpbit == 0xF0) {
                    short tmpst0 = (short) (((short) bytes[j]) & 0x0F);
                    if (tmpst0 == 0) {
                        j += 4;
                        len += 2;
                    } else if ((tmpst0 > 0) && (tmpst0 < 12)) {
                        j += 5;
                        len += 2;
                    } else if (tmpst0 > 11) {
                        j += 6;
                        len += 2;
                    }
                }
            } else {
                j += 1;
                len += 1;
            }
        } while (j <= bytes.length - 1);
        return len;
    }
}
