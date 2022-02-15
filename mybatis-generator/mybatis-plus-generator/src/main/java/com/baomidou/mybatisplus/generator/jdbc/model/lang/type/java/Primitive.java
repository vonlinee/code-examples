package com.baomidou.mybatisplus.generator.jdbc.model.lang.type.java;

public final class Primitive extends JavaDataType {

    public static final Primitive INT = new Primitive("int");
    public static final Primitive BYTE = new Primitive("byte");
    public static final Primitive FLOAT = new Primitive("float");
    public static final Primitive DOUBLE = new Primitive("double");
    public static final Primitive LONG = new Primitive("long");
    public static final Primitive SHORT = new Primitive("short");

    private final String name;

    private Primitive(String name) {
        this.name = name;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    boolean isPrimitive() {
        return true;
    }

    @Override
    boolean isReference() {
        return false;
    }
}
