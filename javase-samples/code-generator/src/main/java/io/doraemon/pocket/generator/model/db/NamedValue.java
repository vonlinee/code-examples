package io.doraemon.pocket.generator.model.db;

public final class NamedValue {

    private static final String NULL = "NULL";

    private final String name;
    private String typeName;
    private final Object value;
    private Class<?> classType;

    public NamedValue(String name, Object obj) {
        this.name = name;
        if ((this.value = obj) != null) {
            this.classType = obj.getClass();
            this.typeName = classType.getName();
        }
        this.classType = Void.class;
        this.typeName = NULL;
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
        return value == null ? NULL : value.toString();
    }
}
