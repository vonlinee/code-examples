package io.devpl.codegen;

/**
 * <a href="https://docs.oracle.com/javase/tutorial/java/nutsandbolts/datatypes.html">...</a>
 */
public enum JavaDataType {

    // 8种基本数据类型
    PRIMITIVE_BYTE("byte", byte.class),
    PRIMITIVE_CHAR("char", char.class),
    PRIMITIVE_BOOLEAN("boolean", boolean.class),
    PRIMITIVE_SHORT("short", short.class),
    PRIMITIVE_INT("int", int.class),
    PRIMITIVE_LONG("long", long.class),
    PRIMITIVE_FLOAT("float", float.class),
    PRIMITIVE_DOUBLE("double", double.class);

    final String name;
    final Class<?> typeClass;

    JavaDataType(String name, Class<?> typeClass) {
        this.name = name;
        this.typeClass = typeClass;
    }
}
