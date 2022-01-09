package cn.example.curator;

import java.beans.Encoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * @author Brave
 * @create 2021-08-13 14:14
 * @description
 **/
public class EncodingExample {
    public static void main(String[] args) {
        String s1 = "一二三";

        byte[] gbk_bytes = s1.getBytes(Charset.forName("GBK"));
        String gbk_str = new String(gbk_bytes);
        System.out.println(gbk_str);

        byte[] utf8_bytes = s1.getBytes(StandardCharsets.UTF_8);
        String utf8_str = new String(utf8_bytes);
        System.out.println(utf8_str);

        byte[] iso8859_1_bytes = s1.getBytes(StandardCharsets.ISO_8859_1);
        String iso8859_1_str = new String(iso8859_1_bytes);
        System.out.println(iso8859_1_str);
    }
}
