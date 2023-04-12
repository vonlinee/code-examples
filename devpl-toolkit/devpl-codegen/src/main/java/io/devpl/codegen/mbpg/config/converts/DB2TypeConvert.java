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
import io.devpl.codegen.mbpg.config.rules.DataType;
import io.devpl.codegen.mbpg.config.rules.JavaType;

/**
 * DB2 字段类型转换
 *
 * @author zhanyao, hanchunlin
 * @since 2018-05-16
 */
public class DB2TypeConvert implements ITypeConvert {
    public static final DB2TypeConvert INSTANCE = new DB2TypeConvert();

    /**
     * @inheritDoc
     */
    @Override
    public DataType processTypeConvert(ProjectConfiguration config, String fieldType) {
        return TypeConverts.use(fieldType)
            .test(TypeConverts.containsAny("char", "text", "json", "enum").then(JavaType.STRING))
            .test(TypeConverts.contains("bigint").then(JavaType.LONG))
            .test(TypeConverts.contains("smallint").then(JavaType.BASE_SHORT))
            .test(TypeConverts.contains("int").then(JavaType.INTEGER))
            .test(TypeConverts.containsAny("date", "time", "year").then(JavaType.DATE))
            .test(TypeConverts.contains("bit").then(JavaType.BOOLEAN))
            .test(TypeConverts.contains("decimal").then(JavaType.BIG_DECIMAL))
            .test(TypeConverts.contains("clob").then(JavaType.CLOB))
            .test(TypeConverts.contains("blob").then(JavaType.BLOB))
            .test(TypeConverts.contains("binary").then(JavaType.BYTE_ARRAY))
            .test(TypeConverts.contains("float").then(JavaType.FLOAT))
            .test(TypeConverts.contains("double").then(JavaType.DOUBLE))
            .or(JavaType.STRING);
    }

}
