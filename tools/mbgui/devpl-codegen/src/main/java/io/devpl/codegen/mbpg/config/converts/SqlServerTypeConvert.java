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

import static io.devpl.codegen.mbpg.config.converts.TypeConverts.contains;
import static io.devpl.codegen.mbpg.config.converts.TypeConverts.containsAny;

import io.devpl.codegen.mbpg.config.GlobalConfig;
import io.devpl.codegen.mbpg.config.ITypeConvert;
import io.devpl.codegen.mbpg.config.rules.IColumnType;
import io.devpl.codegen.mbpg.config.rules.DbColumnType;

/**
 * SQLServer 字段类型转换
 *
 * @author hubin, hanchunlin
 * @since 2017-01-20
 */
public class SqlServerTypeConvert implements ITypeConvert {

    public static final SqlServerTypeConvert INSTANCE = new SqlServerTypeConvert();

    /**
     * @inheritDoc
     */
    @Override
    public IColumnType processTypeConvert(GlobalConfig config, String fieldType) {
        return TypeConverts.use(fieldType)
            .test(containsAny("char", "xml", "text").then(DbColumnType.STRING))
            .test(contains("bigint").then(DbColumnType.LONG))
            .test(contains("int").then(DbColumnType.INTEGER))
            .test(containsAny("date", "time").then(t -> toDateType(config, t)))
            .test(contains("bit").then(DbColumnType.BOOLEAN))
            .test(containsAny("decimal", "numeric").then(DbColumnType.DOUBLE))
            .test(contains("money").then(DbColumnType.BIG_DECIMAL))
            .test(containsAny("binary", "image").then(DbColumnType.BYTE_ARRAY))
            .test(containsAny("float", "real").then(DbColumnType.FLOAT))
            .or(DbColumnType.STRING);
    }

    /**
     * 转换为日期类型
     *
     * @param config 配置信息
     * @param fieldType   类型
     * @return 返回对应的列类型
     */
    public static IColumnType toDateType(GlobalConfig config, String fieldType) {
        switch (config.getDateType()) {
            case SQL_PACK:
                switch (fieldType) {
                    case "date":
                        return DbColumnType.DATE_SQL;
                    case "time":
                        return DbColumnType.TIME;
                    default:
                        return DbColumnType.TIMESTAMP;
                }
            case TIME_PACK:
                switch (fieldType) {
                    case "date":
                        return DbColumnType.LOCAL_DATE;
                    case "time":
                        return DbColumnType.LOCAL_TIME;
                    default:
                        return DbColumnType.LOCAL_DATE_TIME;
                }
            default:
                return DbColumnType.DATE;
        }
    }
}
