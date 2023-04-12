package io.devpl.codegen.meta;

import lombok.Data;

/**
 * 字段信息: 和Java数据库都无关的字段
 */
@Data
public class FieldMetaData {

    /**
     * 名称
     */
    private String name;

    /**
     * 含义及描述信息
     */
    private String description;

    /**
     * 用常量表示数据类型
     * @see java.sql.Types
     */
    private int dataType; // 数据类型
}
