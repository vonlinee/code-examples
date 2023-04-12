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

import io.devpl.codegen.mbpg.config.ProjectConfiguration;
import io.devpl.codegen.mbpg.config.ITypeConvert;
import io.devpl.codegen.mbpg.config.rules.JavaType;
import io.devpl.codegen.mbpg.config.rules.DataType;

import static io.devpl.codegen.mbpg.config.converts.TypeConverts.contains;
import static io.devpl.codegen.mbpg.config.converts.TypeConverts.containsAny;

/**
 * PostgreSQL 字段类型转换
 *
 * @author hubin, hanchunlin
 * @since 2017-01-20
 */
public class PostgreSqlTypeConvert implements ITypeConvert {
    public static final PostgreSqlTypeConvert INSTANCE = new PostgreSqlTypeConvert();

    /**
     * @inheritDoc
     */
    @Override
    public DataType processTypeConvert(ProjectConfiguration config, String fieldType) {
        return TypeConverts.use(fieldType)
            .test(containsAny("char", "text", "json", "enum").then(JavaType.STRING))
            .test(contains("bigint").then(JavaType.LONG))
            .test(contains("int").then(JavaType.INTEGER))
            .test(containsAny("date", "time").then(t -> toDateType(config, t)))
            .test(contains("bit").then(JavaType.BOOLEAN))
            .test(containsAny("decimal", "numeric").then(JavaType.BIG_DECIMAL))
            .test(contains("bytea").then(JavaType.BYTE_ARRAY))
            .test(contains("float").then(JavaType.FLOAT))
            .test(contains("double").then(JavaType.DOUBLE))
            .test(contains("boolean").then(JavaType.BOOLEAN))
            .or(JavaType.STRING);
    }

    /**
     * 转换为日期类型
     *
     * @param config 配置信息
     * @param type   类型
     * @return 返回对应的列类型
     */
    public static DataType toDateType(ProjectConfiguration config, String type) {
        switch (config.getDateType()) {
            case SQL_PACK:
                switch (type) {
                    case "date":
                        return JavaType.DATE_SQL;
                    case "time":
                        return JavaType.TIME;
                    default:
                        return JavaType.TIMESTAMP;
                }
            case TIME_PACK:
                switch (type) {
                    case "date":
                        return JavaType.LOCAL_DATE;
                    case "time":
                        return JavaType.LOCAL_TIME;
                    default:
                        return JavaType.LOCAL_DATE_TIME;
                }
            default:
                return JavaType.DATE;
        }
    }
}
