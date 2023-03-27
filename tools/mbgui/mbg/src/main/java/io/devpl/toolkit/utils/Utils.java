package io.devpl.toolkit.utils;

import cn.hutool.core.text.NamingCase;

public class Utils {

    /**
     * 将下划线方式命名的字符串转换为驼峰式。如果转换前的下划线大写方式命名的字符串为空，则返回空字符串。<br>
     * 例如：hello_world=》helloWorld
     *
     * @param str 转换前的下划线大写方式命名的字符串
     * @return 转换后的驼峰式命名的字符串
     * @see NamingCase#toCamelCase(CharSequence)
     */
    public static String toCamelCase(String str) {
        return NamingCase.toCamelCase(str.toLowerCase());
    }
}
