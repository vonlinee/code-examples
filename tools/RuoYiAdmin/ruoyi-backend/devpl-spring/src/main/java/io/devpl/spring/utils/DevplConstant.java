package io.devpl.spring.utils;

import java.util.regex.Pattern;

public interface DevplConstant {

    String NAME = "devpl";

    /**
     * Devpl内部Spring的配置文件名正则表达式
     */
    Pattern CONFIG_FILE_PATTERN = Pattern.compile("devpl*");
}
