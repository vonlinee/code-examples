package io.maker.generator.lang.type;

public abstract class JavaDataType implements DataType {
    abstract boolean isPrimitive();
    abstract boolean isReference();
}
