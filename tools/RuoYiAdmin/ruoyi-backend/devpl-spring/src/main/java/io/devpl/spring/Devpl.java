package io.devpl.spring;

import java.util.regex.Pattern;

public interface Devpl {

    String NAME = "devpl";

    /**
     * Devpl内部Spring的配置文件名正则表达式
     */
    Pattern CONFIG_FILE_PATTERN = Pattern.compile("devpl*");
}
