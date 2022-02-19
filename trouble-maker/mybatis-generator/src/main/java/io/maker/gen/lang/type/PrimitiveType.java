package io.maker.gen.lang.type;

public final class PrimitiveType extends JavaDataType {

    public static final PrimitiveType INT = new PrimitiveType("int");
    public static final PrimitiveType BYTE = new PrimitiveType("byte");
    public static final PrimitiveType FLOAT = new PrimitiveType("float");
    public static final PrimitiveType DOUBLE = new PrimitiveType("double");
    public static final PrimitiveType LONG = new PrimitiveType("long");
    public static final PrimitiveType SHORT = new PrimitiveType("short");

    private final String name;

    private PrimitiveType(String name) {
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
