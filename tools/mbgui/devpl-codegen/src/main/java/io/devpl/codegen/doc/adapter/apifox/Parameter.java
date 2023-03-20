package io.devpl.codegen.doc.adapter.apifox;

/**
 * Apifox请求参数
 */
public class Parameter {

    /**
     * 唯一ID
     */
    private String id;

    /**
     * 一般是驼峰
     */
    private String name;

    /**
     * 是否必须
     */
    private boolean required;

    /**
     * 描述信息
     */
    private String description;

    /**
     * 示例值，可以是任意类型，因此用Object表示
     */
    private Object example;

    /**
     * 数据类型: string, array, integer
     * OpenAPI规范定义的数据类型
     */
    private String type;
}
