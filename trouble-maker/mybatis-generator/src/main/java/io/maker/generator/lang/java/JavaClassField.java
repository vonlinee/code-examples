package io.maker.generator.lang.java;

import java.io.Serializable;
import java.lang.reflect.Modifier;
import java.util.List;

import io.maker.generator.lang.type.JavaDataType;

public class JavaClassField<T> implements Serializable {

    private String name;
    private Class<T> type;
    private JavaDataType dataType;
    private T intialValue;
    private List<Modifier> modifiers;

    public JavaClassField(String name, Class<T> type, JavaDataType dataType, T intialValue, List<Modifier> modifiers) {
        this.name = name;
        this.type = type;
        this.dataType = dataType;
        this.intialValue = intialValue;
        this.modifiers = modifiers;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Class<T> getType() {
        return type;
    }

    public void setType(Class<T> type) {
        this.type = type;
    }

    public JavaDataType getDataType() {
        return dataType;
    }

    public void setDataType(JavaDataType dataType) {
        this.dataType = dataType;
    }

    public T getIntialValue() {
        return intialValue;
    }

    public void setIntialValue(T intialValue) {
        this.intialValue = intialValue;
    }

    public List<Modifier> getModifiers() {
        return modifiers;
    }

    public void setModifiers(List<Modifier> modifiers) {
        this.modifiers = modifiers;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        if (modifiers.size() > 0) {
            for (Modifier modifier : modifiers) {
                builder.append(modifier.toString()).append(" ");
            }
        }
        builder.append(name);
        if (intialValue != null) {
            builder.append(" = ").append(intialValue);
        }
        return builder.append(";").toString();
    }
}
