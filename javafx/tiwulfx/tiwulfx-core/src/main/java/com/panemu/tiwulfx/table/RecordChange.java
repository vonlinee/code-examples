package com.panemu.tiwulfx.table;

/**
 * @param <R> 行数据类型
 * @param <C> 列数据类型
 */
public class RecordChange<R, C> {

    private final R record;
    private String propertyName;
    private C oldValue;
    private C newValue;

    public RecordChange(R record, String propertyName, C oldValue, C newValue) {
        this.record = record;
        this.propertyName = propertyName;
        this.oldValue = oldValue;
        this.newValue = newValue;
    }

    public C getOldValue() {
        return oldValue;
    }

    public void setOldValue(C oldValue) {
        this.oldValue = oldValue;
    }

    public C getNewValue() {
        return newValue;
    }

    public void setNewValue(C newValue) {
        this.newValue = newValue;
    }

    public R getRecord() {
        return this.record;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    @Override
    public String toString() {
        return "RecordChange{" + "record=" + record + ", propertyName=" + propertyName + ", oldValue=" + oldValue + ", newValue=" + newValue + '}';
    }

}
