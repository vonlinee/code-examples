package io.devpl.codegen.toolkit;

import io.devpl.sdk.lang.Interpolations;

import java.util.HashMap;
import java.util.Map;

/**
 * MyBatis工具类
 */
public class MyBatisUtils {

    public static void main(String[] args) {
        System.out.println(ifTest("name", "param.username"));
    }

    /**
     * ifTest("name", "param.username") => 结果如下：
     * <if test="param.username != null and param.username != ''">
     * name = #{param.username}
     * </if>
     * @param columnName
     * @param paramName
     * @return
     */
    public static String ifTest(String columnName, String paramName) {
        Map<String, String> map = new HashMap<>();
        map.put("columnName", columnName);
        map.put("paramName", paramName);
        return Interpolations.named("<if test=\"{paramName} != null and {paramName} != ''\">\n\t{columnName} = #{{paramName}}\n</if>", map);
    }

    public static String foreach(String paramName) {
        Map<String, String> map = new HashMap<>();
        map.put("paramName", paramName);
        return null;
    }
}
