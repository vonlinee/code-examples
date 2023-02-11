package io.devpl.toolkit.fxui.model;

import lombok.Data;

/**
 * 元字段信息
 */
@Data
public class MetaField {

    /**
     * 主键ID
     */
    private String fieldId;

    /**
     * 字段名称
     */
    private String fieldName;

    /**
     * 字段含义
     */
    private String fieldDescription;
}
