package io.doraemon.pocket.generator.model.lang.type.java;

public class Reference extends JavaDataType {
    @Override
    public String name() {
        return null;
    }

    @Override
    boolean isPrimitive() {
        return false;
    }

    @Override
    boolean isReference() {
        return false;
    }
}
