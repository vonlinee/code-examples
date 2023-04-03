package io.devpl.toolkit.utils;

import org.apache.tomcat.util.codec.binary.Base64;

/**
 * 加解密工具类
 */
public final class SecurityUtils {

    /**
     * Java中的Base64加密
     * Base64不是真正的加密，只是对字符串进行编码解码而已。
     * 主要作用是不让人一眼就可以看出字符串是什么值，有什么作用。
     *
     * @return 解密后的字符串
     */
    public static String decodeBase64(String src) {
        return new String(Base64.decodeBase64(src));
    }
}
