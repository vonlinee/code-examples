package io.devpl.toolkit.fxui.model;

import lombok.Data;

/**
 * 元字段信息
 */
@Data
public class MetaField {

    /**
     * 是否选中，非数据库字段
     */
    private boolean selected;


    /**
     * 主键ID
     */
    private String fieldId;

    /**
     * 字段名称
     */
    private String fieldName;

    /**
     * 字段值
     */
    private String fieldValue;

    /**
     * 字段含义
     */
    private String fieldDescription;
}
