package com.baomidou.mybatisplus.generator.jdbc.model.lang.type;


import com.baomidou.mybatisplus.generator.jdbc.model.lang.type.DataType;

public abstract class JavaDataType implements DataType {
    abstract boolean isPrimitive();

    abstract boolean isReference();
}
