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
import io.devpl.codegen.mbpg.config.rules.JavaType;
import io.devpl.codegen.mbpg.config.rules.DataType;

/**
 * MYSQL 数据库字段类型转换
 *
 * @author hubin, hanchunlin
 * @since 2017-01-20
 */
public class FirebirdTypeConvert implements TypeMapping {
    public static final FirebirdTypeConvert INSTANCE = new FirebirdTypeConvert();

    /**
     * @inheritDoc
     */
    @Override
    public DataType processTypeConvert(ProjectConfiguration config, String fieldType) {
        return TypeConverts.use(fieldType)
            .test(TypeConverts.containsAny("cstring", "text").then(JavaType.STRING))
            .test(TypeConverts.contains("short").then(JavaType.SHORT))
            .test(TypeConverts.contains("long").then(JavaType.LONG))
            .test(TypeConverts.contains("float").then(JavaType.FLOAT))
            .test(TypeConverts.contains("double").then(JavaType.DOUBLE))
            .test(TypeConverts.contains("blob").then(JavaType.BLOB))
            .test(TypeConverts.contains("int64").then(JavaType.LONG))
            .test(TypeConverts.containsAny("date", "time", "year").then(t -> toDateType(config, t)))
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
            case ONLY_DATE:
                return JavaType.DATE;
            case SQL_PACK:
                switch (type) {
                    case "date":
                    case "year":
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
                    case "year":
                        return JavaType.YEAR;
                    default:
                        return JavaType.LOCAL_DATE_TIME;
                }
        }
        return JavaType.STRING;
    }

}
