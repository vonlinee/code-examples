package io.devpl.codegen.mbpg.config.converts;

import io.devpl.codegen.mbpg.config.GlobalConfig;
import io.devpl.codegen.mbpg.config.ITypeConvert;
import io.devpl.codegen.mbpg.config.rules.JavaType;
import io.devpl.codegen.mbpg.config.rules.IColumnType;

/**
 * MYSQL 数据库字段类型转换
 * bit类型数据转换 bit(1) -> Boolean类型  bit(2->64)  -> Byte类型
 * @author hubin, hanchunlin, xiaoliang
 * @since 2017-01-20
 */
public class MySqlTypeConvert implements ITypeConvert {
    public static final MySqlTypeConvert INSTANCE = new MySqlTypeConvert();

    /**
     * @inheritDoc
     */
    @Override
    public IColumnType processTypeConvert(GlobalConfig config, String fieldType) {
        return TypeConverts
                .use(fieldType)
                .test(TypeConverts.containsAny("char", "text", "json", "enum").then(JavaType.STRING))
                .test(TypeConverts.contains("bigint").then(JavaType.LONG))
                .test(TypeConverts.containsAny("tinyint(1)", "bit(1)").then(JavaType.BOOLEAN))
                .test(TypeConverts.contains("bit").then(JavaType.BYTE))
                .test(TypeConverts.contains("int").then(JavaType.INTEGER))
                .test(TypeConverts.contains("decimal").then(JavaType.BIG_DECIMAL))
                .test(TypeConverts.contains("clob").then(JavaType.CLOB))
                .test(TypeConverts.contains("blob").then(JavaType.BLOB))
                .test(TypeConverts.contains("binary").then(JavaType.BYTE_ARRAY))
                .test(TypeConverts.contains("float").then(JavaType.FLOAT))
                .test(TypeConverts.contains("double").then(JavaType.DOUBLE))
                .test(TypeConverts.containsAny("date", "time", "year").then(t -> toDateType(config, t)))
                .or(JavaType.STRING);
    }

    /**
     * 转换为日期类型
     * @param config 配置信息
     * @param type   类型
     * @return 返回对应的列类型
     */
    public static IColumnType toDateType(GlobalConfig config, String type) {
        String dateType = type.replaceAll("\\(\\d+\\)", "");
        switch (config.getDateType()) {
            case ONLY_DATE:
                return JavaType.DATE;
            case SQL_PACK:
                switch (dateType) {
                    case "date":
                    case "year":
                        return JavaType.DATE_SQL;
                    case "time":
                        return JavaType.TIME;
                    default:
                        return JavaType.TIMESTAMP;
                }
            case TIME_PACK:
                switch (dateType) {
                    case "date":
                        return JavaType.LOCAL_DATE;
                    case "time":
                        return JavaType.LOCAL_TIME;
                    case "year":
                        return JavaType.YEAR;
                    default:
                        return JavaType.LOCAL_DATE_TIME;
                }
        }
        return JavaType.STRING;
    }
}
