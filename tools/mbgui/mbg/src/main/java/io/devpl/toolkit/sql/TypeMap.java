package io.devpl.toolkit.sql;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * @see com.google.common.collect.BiMap
 */
public class TypeMap {

    private final Map<Class<?>, SqlType> typeMappinngMap = new HashMap<>();

    public TypeMap() {
        // 默认类型映射
        typeMappinngMap.put(int.class, SqlType.INT);
        typeMappinngMap.put(boolean.class, SqlType.TINYINT);
        typeMappinngMap.put(float.class, SqlType.FLOAT);
        typeMappinngMap.put(double.class, SqlType.DECIMAL);
        typeMappinngMap.put(short.class, SqlType.INT);
        typeMappinngMap.put(byte.class, SqlType.INT);
        typeMappinngMap.put(char.class, SqlType.CHAR);
        typeMappinngMap.put(Boolean.class, SqlType.TINYINT);
        typeMappinngMap.put(Short.class, SqlType.INT);
        typeMappinngMap.put(Integer.class, SqlType.INT);
        typeMappinngMap.put(Float.class, SqlType.FLOAT);
        typeMappinngMap.put(Double.class, SqlType.DECIMAL);
        typeMappinngMap.put(Long.class, SqlType.BIGINT);
        typeMappinngMap.put(LocalDateTime.class, SqlType.DATETIME);
        typeMappinngMap.put(LocalDate.class, SqlType.DATE);
        typeMappinngMap.put(String.class, SqlType.VARCHAR);
        typeMappinngMap.put(CharSequence.class, SqlType.VARCHAR);
    }

    public SqlType get(Type type) {
        return typeMappinngMap.get(type);
    }
}
