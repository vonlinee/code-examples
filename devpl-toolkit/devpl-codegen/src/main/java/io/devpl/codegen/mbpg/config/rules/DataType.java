package io.devpl.codegen.mbpg.config.rules;

/**
 * 获取实体类字段属性类信息接口
 */
public interface DataType {

    /**
     * 获取字段类型
     *
     * @return 字段类型，一般是类型简写
     */
    String getType();

    /**
     * 获取字段类型完整名
     *
     * @return 字段类型完整名
     */
    String getQualifiedName();
}
