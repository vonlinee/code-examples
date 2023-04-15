/*
 * Copyright (c) 2011-2021, baomidou (jobob@qq.com).
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * <p>
 * https://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package io.devpl.codegen.mbpg.config.converts;

import io.devpl.codegen.api.TypeMapping;
import io.devpl.codegen.mbpg.config.ProjectConfiguration;
import io.devpl.codegen.mbpg.config.rules.DateTimeType;
import io.devpl.codegen.mbpg.config.rules.JavaType;
import io.devpl.codegen.mbpg.config.rules.DataType;

/**
 * KingbaseES 字段类型转换
 *
 * @author kingbase, hanchunlin
 * @since 2019-10-12
 */
public class OscarTypeConvert implements TypeMapping {
    public static final OscarTypeConvert INSTANCE = new OscarTypeConvert();

    /**
     * @param globalConfig 全局配置
     * @param fieldType    字段类型
     * @return 返回对应的字段类型
     */
    @Override
    public DataType processTypeConvert(ProjectConfiguration globalConfig, String fieldType) {
        return TypeConverts.use(fieldType)
            .test(TypeConverts
                    .containsAny("CHARACTER", "char", "varchar", "text", "character varying").then(JavaType.STRING))
            .test(TypeConverts.containsAny("bigint", "int8").then(JavaType.LONG))
            .test(TypeConverts
                    .containsAny("int", "int1", "int2", "int3", "int4", "tinyint", "integer").then(JavaType.INTEGER))
            .test(TypeConverts.containsAny("date", "time", "timestamp").then(p -> toDateType(globalConfig, p)))
            .test(TypeConverts.containsAny("bit", "boolean").then(JavaType.BOOLEAN))
            .test(TypeConverts.containsAny("decimal", "numeric", "number").then(JavaType.BIG_DECIMAL))
            .test(TypeConverts.contains("clob").then(JavaType.CLOB))
            .test(TypeConverts.contains("blob").then(JavaType.BYTE_ARRAY))
            .test(TypeConverts.contains("float").then(JavaType.FLOAT))
            .test(TypeConverts.containsAny("double", "real", "float4", "float8").then(JavaType.DOUBLE))
            .or(JavaType.STRING);
    }

    /**
     * 转换为日期类型
     *
     * @param config 配置信息
     * @param type   类型
     * @return 返回对应的列类型
     */
    private DataType toDateType(ProjectConfiguration config, String type) {
        DateTimeType dateType = config.getDateType();
        if (dateType == DateTimeType.SQL_PACK) {
            switch (type) {
                case "date":
                    return JavaType.DATE_SQL;
                case "time":
                    return JavaType.TIME;
                default:
                    return JavaType.TIMESTAMP;
            }
        } else if (dateType == DateTimeType.TIME_PACK) {
            switch (type) {
                case "date":
                    return JavaType.LOCAL_DATE;
                case "time":
                    return JavaType.LOCAL_TIME;
                default:
                    return JavaType.LOCAL_DATE_TIME;
            }
        }
        return JavaType.DATE;
    }

}
