package io.devpl.codegen.mbpg.config.converts;

import io.devpl.codegen.mbpg.config.GlobalConfig;
import io.devpl.codegen.mbpg.config.ITypeConvert;
import io.devpl.codegen.mbpg.config.rules.DbColumnType;
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
                .test(TypeConverts.containsAny("char", "text", "json", "enum").then(DbColumnType.STRING))
                .test(TypeConverts.contains("bigint").then(DbColumnType.LONG))
                .test(TypeConverts.containsAny("tinyint(1)", "bit(1)").then(DbColumnType.BOOLEAN))
                .test(TypeConverts.contains("bit").then(DbColumnType.BYTE))
                .test(TypeConverts.contains("int").then(DbColumnType.INTEGER))
                .test(TypeConverts.contains("decimal").then(DbColumnType.BIG_DECIMAL))
                .test(TypeConverts.contains("clob").then(DbColumnType.CLOB))
                .test(TypeConverts.contains("blob").then(DbColumnType.BLOB))
                .test(TypeConverts.contains("binary").then(DbColumnType.BYTE_ARRAY))
                .test(TypeConverts.contains("float").then(DbColumnType.FLOAT))
                .test(TypeConverts.contains("double").then(DbColumnType.DOUBLE))
                .test(TypeConverts.containsAny("date", "time", "year").then(t -> toDateType(config, t)))
                .or(DbColumnType.STRING);
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
                return DbColumnType.DATE;
            case SQL_PACK:
                switch (dateType) {
                    case "date":
                    case "year":
                        return DbColumnType.DATE_SQL;
                    case "time":
                        return DbColumnType.TIME;
                    default:
                        return DbColumnType.TIMESTAMP;
                }
            case TIME_PACK:
                switch (dateType) {
                    case "date":
                        return DbColumnType.LOCAL_DATE;
                    case "time":
                        return DbColumnType.LOCAL_TIME;
                    case "year":
                        return DbColumnType.YEAR;
                    default:
                        return DbColumnType.LOCAL_DATE_TIME;
                }
        }
        return DbColumnType.STRING;
    }
}
