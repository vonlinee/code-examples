package com.panemu.tiwulfx.common;

import java.io.Serializable;

/**
 * Table Query Criteria
 * @param <T> table column data type
 */
public class TableCriteria<T> implements Serializable {

    public enum Condition {
        in("in"),
        not_in("not.in"),
        eq("="),
        ne("<>"),
        le("<="),
        lt("<"),
        ge(">="),
        gt(">"),
        is_null("null"),
        is_not_null("not.null"),
        like_begin("start.with"),
        like_end("end.with"),
        like_anywhere("contains"),
        ilike_begin("start.with"),
        ilike_end("end.with"),
        ilike_anywhere("contains");
        private final String description;

        Condition(String desc) {
            this.description = desc;
        }

        @Override
        public String toString() {
            return description;
        }
    }

    private String attributeName;
    private Condition operator;
    private T value;

    public TableCriteria(String attributeName, Condition operator, T value) {
        this.attributeName = attributeName;
        this.operator = operator;
        this.value = value;
    }

    public String getAttributeName() {
        return attributeName;
    }

    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }

    public Condition getOperator() {
        return operator;
    }

    public void setOperator(Condition operator) {
        this.operator = operator;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }
}
