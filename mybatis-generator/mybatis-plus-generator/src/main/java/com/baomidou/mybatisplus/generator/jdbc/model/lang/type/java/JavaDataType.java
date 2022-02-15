package com.baomidou.mybatisplus.generator.jdbc.model.lang.type.java;


import com.baomidou.mybatisplus.generator.jdbc.model.lang.type.DataType;

public abstract class JavaDataType implements DataType {
    abstract boolean isPrimitive();

    abstract boolean isReference();
}
