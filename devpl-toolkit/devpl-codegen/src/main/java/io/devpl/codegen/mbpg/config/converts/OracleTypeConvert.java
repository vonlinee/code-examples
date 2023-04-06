package io.devpl.codegen.mbpg.config.converts;

import io.devpl.codegen.mbpg.config.GlobalConfig;
import io.devpl.codegen.mbpg.config.ITypeConvert;
import io.devpl.codegen.mbpg.config.rules.JavaType;
import io.devpl.codegen.mbpg.config.rules.IColumnType;

/**
 * Oracle 数据库生成对应实体类时字段类型转换，跟据 Oracle 中的数据类型，返回对应的 Java 类型
 *
 * @author hubin, hanchunlin
 * @since 2017-01-20
 */
public class OracleTypeConvert implements ITypeConvert {
    public static final OracleTypeConvert INSTANCE = new OracleTypeConvert();

    /**
     * 处理类型转换
     *
     * @param config    全局配置
     * @param fieldType 字段类型
     * @return 返回的对应的列类型
     */
    @Override
    public IColumnType processTypeConvert(GlobalConfig config, String fieldType) {
        return TypeConverts.use(fieldType)
            .test(TypeConverts.containsAny("char", "clob").then(JavaType.STRING))
            .test(TypeConverts.containsAny("date", "timestamp").then(p -> toDateType(config)))
            .test(TypeConverts.contains("number").then(OracleTypeConvert::toNumberType))
            .test(TypeConverts.contains("float").then(JavaType.FLOAT))
            .test(TypeConverts.contains("blob").then(JavaType.BLOB))
            .test(TypeConverts.containsAny("binary", "raw").then(JavaType.BYTE_ARRAY))
            .or(JavaType.STRING);
    }

    /**
     * 将对应的类型名称转换为对应的 java 类类型
     * <p>
     * String.valueOf(Integer.MAX_VALUE).length() == 10
     * Integer 不一定能装下 10 位的数字
     * <p>
     * String.valueOf(Long.MAX_VALUE).length() == 19
     * Long 不一定能装下 19 位的数字
     *
     * @param typeName 类型名称
     * @return 返回列类型
     */
    private static IColumnType toNumberType(String typeName) {
        if (typeName.matches("number\\([0-9]\\)")) {
            return JavaType.INTEGER;
        } else if (typeName.matches("number\\(1[0-8]\\)")) {
            return JavaType.LONG;
        }
        return JavaType.BIG_DECIMAL;
    }

    /**
     * 当前时间为字段类型，根据全局配置返回对应的时间类型
     *
     * @param config 全局配置
     * @return 时间类型
     * @see GlobalConfig#getDateType()
     */
    protected static IColumnType toDateType(GlobalConfig config) {
        switch (config.getDateType()) {
            case ONLY_DATE:
                return JavaType.DATE;
            case SQL_PACK:
                return JavaType.TIMESTAMP;
            case TIME_PACK:
                return JavaType.LOCAL_DATE_TIME;
            default:
                return JavaType.STRING;
        }
    }

}
