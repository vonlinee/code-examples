package io.maker.gen.lang.type;

public abstract class JavaDataType implements DataType {
    abstract boolean isPrimitive();
    abstract boolean isReference();
}
