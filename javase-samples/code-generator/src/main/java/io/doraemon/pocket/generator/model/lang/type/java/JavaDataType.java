package io.doraemon.pocket.generator.model.lang.type.java;

import io.doraemon.pocket.generator.model.lang.type.DataType;

public abstract class JavaDataType implements DataType {
    abstract boolean isPrimitive();
    abstract boolean isReference();
}
