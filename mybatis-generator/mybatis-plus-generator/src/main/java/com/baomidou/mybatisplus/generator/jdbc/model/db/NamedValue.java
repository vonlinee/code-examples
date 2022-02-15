package com.baomidou.mybatisplus.generator.jdbc.model.db;

import java.io.Serializable;

public final class NamedValue extends JavaType implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final String NULL_STRING = "NULL";
    public static final String NULL_REFERENCE = null;

    private final String name;
    private final String typeName;
    private final Object value;

    public NamedValue(String name, Object obj) {
        this.name = name;
        if ((this.value = obj) != null) {
            this.classType = obj.getClass();
            this.typeName = classType.getName();
        } else {
            this.classType = Void.class;
            this.typeName = NULL_STRING;
        }
    }

    public String getName() {
        return name;
    }

    public String getTypeName() {
        return typeName;
    }

    public Object getValue() {
        return value;
    }

    public Class<?> getType() {
        return classType;
    }

    public boolean isNull() {
        return value == null;
    }

    @Override
    public String toString() {
        return value == null ? NULL_STRING : value.toString();
    }
}
