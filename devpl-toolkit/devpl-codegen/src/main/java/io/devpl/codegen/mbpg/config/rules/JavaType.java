package io.devpl.codegen.mbpg.config.rules;

import java.util.HashMap;
import java.util.Map;

/**
 * Java实体类字段类型
 * <a href="https://docs.oracle.com/javase/tutorial/java/nutsandbolts/datatypes.html">...</a>
 */
public enum JavaType implements DataType {
    // 基本类型
    BASE_BYTE("byte", null),
    BASE_SHORT("short", null),
    BASE_CHAR("char", null),
    BASE_INT("int", null),
    BASE_LONG("long", null),
    BASE_FLOAT("float", null),
    BASE_DOUBLE("double", null),
    BASE_BOOLEAN("boolean", null),

    // 包装类型
    BYTE("Byte", "java.lang.Byte"),
    SHORT("Short", "java.lang.Short"),
    CHARACTER("Character", "java.lang.Character"),
    INTEGER("Integer", "java.lang.Integer"),
    LONG("Long", "java.lang.Long"),
    FLOAT("Float", "java.lang.Float"),
    DOUBLE("Double", "java.lang.Double"),
    BOOLEAN("Boolean", "java.lang.Boolean"),
    STRING("String", "java.lang.String"),

    // sql 包下数据类型
    DATE_SQL("Date", "java.sql.Date"),
    TIME("Time", "java.sql.Time"),
    TIMESTAMP("Timestamp", "java.sql.Timestamp"),
    BLOB("Blob", "java.sql.Blob"),
    CLOB("Clob", "java.sql.Clob"),

    // java8 新时间类型
    LOCAL_DATE("LocalDate", "java.time.LocalDate"),
    LOCAL_TIME("LocalTime", "java.time.LocalTime"),
    YEAR("Year", "java.time.Year"),
    YEAR_MONTH("YearMonth", "java.time.YearMonth"),
    LOCAL_DATE_TIME("LocalDateTime", "java.time.LocalDateTime"),
    INSTANT("Instant", "java.time.Instant"),

    // 其他杂类
    MAP("Map", "java.util.Map"),
    OBJECT("Object", "java.lang.Object"),
    DATE("Date", "java.util.Date"),
    BIG_INTEGER("BigInteger", "java.math.BigInteger"),
    BIG_DECIMAL("BigDecimal", "java.math.BigDecimal"),

    // 数组类型
    OBJECT_ARRAY("Object[]", "java.lang.Object[]"),
    BYTE_ARRAY("byte[]", null),
    ANY_ARRAY("*[]", null),

    UNKNOWN(OBJECT),
    ;

    /**
     * 类型
     */
    private final String type;

    /**
     * 包路径
     */
    private final String qualifiedName;

    JavaType(JavaType type) {
        this.type = type.type;
        this.qualifiedName = type.qualifiedName;
    }

    JavaType(final String type, final String qulifiedName) {
        this.type = type;
        this.qualifiedName = qulifiedName;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public String getQualifiedName() {
        return qualifiedName;
    }

    public static Map<String, JavaType> typeMap() {
        HashMap<String, JavaType> map = new HashMap<>();
        for (JavaType type : values()) {
            if (type.getType() != null) {
                map.put(type.getType(), type);
            }
            if (type.getQualifiedName() != null) {
                map.put(type.getQualifiedName(), type);
            }
        }
        return map;
    }
}
