package io.devpl.tookit.fxui.model;

import java.util.Objects;

/**
 * 字段信息
 */
public class FieldSpec {

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

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public String getFieldId() {
        return fieldId;
    }

    public void setFieldId(String fieldId) {
        this.fieldId = fieldId;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldValue() {
        return fieldValue;
    }

    public void setFieldValue(String fieldValue) {
        this.fieldValue = fieldValue;
    }

    public String getFieldDescription() {
        return fieldDescription;
    }

    public void setFieldDescription(String fieldDescription) {
        this.fieldDescription = fieldDescription;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof FieldSpec) {
            return Objects.equals(this.fieldName, ((FieldSpec) obj).fieldName);
        }
        return false;
    }
}
