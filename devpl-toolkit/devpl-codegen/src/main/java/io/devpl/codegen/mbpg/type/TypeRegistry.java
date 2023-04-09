package io.devpl.codegen.mbpg.type;

import io.devpl.codegen.mbpg.config.GlobalConfig;
import io.devpl.codegen.mbpg.config.po.TableField;
import io.devpl.codegen.mbpg.config.rules.DateTimeType;
import io.devpl.codegen.mbpg.config.rules.JavaType;
import io.devpl.codegen.mbpg.config.rules.DataType;

import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

/**
 * 类型注册处理类
 */
public class TypeRegistry {

    private final GlobalConfig globalConfig;

    private final Map<Integer, DataType> typeMap = new HashMap<>();

    public TypeRegistry(GlobalConfig globalConfig) {
        this.globalConfig = globalConfig;
        // byte[]
        typeMap.put(Types.BINARY, JavaType.BYTE_ARRAY);
        typeMap.put(Types.BLOB, JavaType.BYTE_ARRAY);
        typeMap.put(Types.LONGVARBINARY, JavaType.BYTE_ARRAY);
        typeMap.put(Types.VARBINARY, JavaType.BYTE_ARRAY);
        // byte
        typeMap.put(Types.TINYINT, JavaType.BYTE);
        // long
        typeMap.put(Types.BIGINT, JavaType.LONG);
        // boolean
        typeMap.put(Types.BIT, JavaType.BOOLEAN);
        typeMap.put(Types.BOOLEAN, JavaType.BOOLEAN);
        // short
        typeMap.put(Types.SMALLINT, JavaType.SHORT);
        // string
        typeMap.put(Types.CHAR, JavaType.STRING);
        typeMap.put(Types.CLOB, JavaType.STRING);
        typeMap.put(Types.VARCHAR, JavaType.STRING);
        typeMap.put(Types.LONGVARCHAR, JavaType.STRING);
        typeMap.put(Types.LONGNVARCHAR, JavaType.STRING);
        typeMap.put(Types.NCHAR, JavaType.STRING);
        typeMap.put(Types.NCLOB, JavaType.STRING);
        typeMap.put(Types.NVARCHAR, JavaType.STRING);
        // date
        typeMap.put(Types.DATE, JavaType.DATE);
        // timestamp
        typeMap.put(Types.TIMESTAMP, JavaType.TIMESTAMP);
        // double
        typeMap.put(Types.FLOAT, JavaType.DOUBLE);
        typeMap.put(Types.REAL, JavaType.DOUBLE);
        // int
        typeMap.put(Types.INTEGER, JavaType.INTEGER);
        // bigDecimal
        typeMap.put(Types.NUMERIC, JavaType.BIG_DECIMAL);
        typeMap.put(Types.DECIMAL, JavaType.BIG_DECIMAL);
        // TODO 类型需要补充完整
    }

    public DataType getColumnType(TableField.MetaInfo metaInfo) {
        // TODO 是否用包装类??? 可以尝试判断字段是否允许为null来判断是否用包装类
        int typeCode = metaInfo.getJdbcType().TYPE_CODE;
        switch (typeCode) {
            // TODO 需要增加类型处理，尚未补充完整
            case Types.BIT:
                return getBitType(metaInfo);
            case Types.DATE:
                return getDateType(metaInfo);
            case Types.TIME:
                return getTimeType(metaInfo);
            case Types.DECIMAL:
            case Types.NUMERIC:
                return getNumber(metaInfo);
            case Types.TIMESTAMP:
                return getTimestampType(metaInfo);
            default:
                return typeMap.getOrDefault(typeCode, JavaType.OBJECT);
        }
    }

    private DataType getBitType(TableField.MetaInfo metaInfo) {
        if (metaInfo.getLength() > 1) {
            return JavaType.BYTE_ARRAY;
        }
        return JavaType.BOOLEAN;
    }

    private DataType getNumber(TableField.MetaInfo metaInfo) {
        if (metaInfo.getScale() > 0 || metaInfo.getLength() > 18) {
            return typeMap.get(metaInfo.getJdbcType().TYPE_CODE);
        } else if (metaInfo.getLength() > 9) {
            return JavaType.LONG;
        } else if (metaInfo.getLength() > 4) {
            return JavaType.INTEGER;
        } else {
            return JavaType.SHORT;
        }
    }

    private DataType getDateType(TableField.MetaInfo metaInfo) {
        JavaType dbColumnType;
        DateTimeType dateType = globalConfig.getDateType();
        switch (dateType) {
            case SQL_PACK:
                dbColumnType = JavaType.DATE_SQL;
                break;
            case TIME_PACK:
                dbColumnType = JavaType.LOCAL_DATE;
                break;
            default:
                dbColumnType = JavaType.DATE;
        }
        return dbColumnType;
    }

    private DataType getTimeType(TableField.MetaInfo metaInfo) {
        JavaType dbColumnType;
        DateTimeType dateType = globalConfig.getDateType();
        if (dateType == DateTimeType.TIME_PACK) {
            dbColumnType = JavaType.LOCAL_TIME;
        } else {
            dbColumnType = JavaType.TIME;
        }
        return dbColumnType;
    }

    private DataType getTimestampType(TableField.MetaInfo metaInfo) {
        JavaType dbColumnType;
        DateTimeType dateType = globalConfig.getDateType();
        if (dateType == DateTimeType.TIME_PACK) {
            dbColumnType = JavaType.LOCAL_DATE_TIME;
        } else if (dateType == DateTimeType.ONLY_DATE) {
            dbColumnType = JavaType.DATE;
        } else {
            dbColumnType = JavaType.TIMESTAMP;
        }
        return dbColumnType;
    }
}
