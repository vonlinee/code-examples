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

/**
 * SQLite 字段类型转换
 *
 * @author chen_wj, hanchunlin
 * @since 2019-05-08
 */
public class SqliteTypeConvert implements ITypeConvert {
    public static final SqliteTypeConvert INSTANCE = new SqliteTypeConvert();

    /**
     * @inheritDoc
     * @see MySqlTypeConvert#toDateType(ProjectConfiguration, String)
     */
    @Override
    public DataType processTypeConvert(ProjectConfiguration config, String fieldType) {
        return TypeConverts.use(fieldType)
            .test(TypeConverts.contains("bigint").then(JavaType.LONG))
            .test(TypeConverts.containsAny("tinyint(1)", "boolean").then(JavaType.BOOLEAN))
            .test(TypeConverts.contains("int").then(JavaType.INTEGER))
            .test(TypeConverts.containsAny("text", "char", "enum").then(JavaType.STRING))
            .test(TypeConverts.containsAny("decimal", "numeric").then(JavaType.BIG_DECIMAL))
            .test(TypeConverts.contains("clob").then(JavaType.CLOB))
            .test(TypeConverts.contains("blob").then(JavaType.BLOB))
            .test(TypeConverts.contains("float").then(JavaType.FLOAT))
            .test(TypeConverts.contains("double").then(JavaType.DOUBLE))
            .test(TypeConverts.containsAny("date", "time", "year").then(t -> MySqlTypeConvert.toDateType(config, t)))
            .or(JavaType.STRING);
    }

}
