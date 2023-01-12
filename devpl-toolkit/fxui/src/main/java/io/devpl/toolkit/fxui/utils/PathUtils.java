package io.devpl.toolkit.fxui.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

public class PathUtils {

    /**
     * 解析失败原样返回
     * @param src
     * @return
     */
    public static String decodeUrl(String src) {
        try {
            return URLDecoder.decode(src, StandardCharsets.UTF_8.displayName());
        } catch (UnsupportedEncodingException e) {
            return src;
        }
    }
}
