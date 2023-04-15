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
import io.devpl.codegen.api.TypeMapping;
import io.devpl.codegen.mbpg.config.rules.JavaType;
import io.devpl.codegen.mbpg.config.rules.DataType;

import static io.devpl.codegen.mbpg.config.converts.TypeConverts.contains;
import static io.devpl.codegen.mbpg.config.converts.TypeConverts.containsAny;

/**
 * DM 字段类型转换
 *
 * @author halower, hanchunlin, daiby
 * @since 2019-06-27
 */
public class DmTypeConvert implements TypeMapping {
    public static final DmTypeConvert INSTANCE = new DmTypeConvert();

    /**
     * 字符数据类型: CHAR,CHARACTER,VARCHAR
     * <p>
     * 数值数据类型: NUMBER,NUMERIC,DECIMAL,DEC,MONEY,BIT,BOOL,BOOLEAN,INTEGER,INT,BIGINT,TINYINT,BYTE,SMALLINT,BINARY,
     * VARBINARY
     * <p>
     * 近似数值数据类型: FLOAT
     * <p>
     * DOUBLE, DOUBLE PRECISION,REAL
     * <p>
     * 日期时间数据类型
     * <p>
     * 多媒体数据类型: TEXT,LONGVARCHAR,CLOB,BLOB,IMAGE
     *
     * @param config    全局配置
     * @param fieldType 字段类型
     * @return 对应的数据类型
     * @inheritDoc
     */
    @Override
    public DataType processTypeConvert(ProjectConfiguration config, String fieldType) {
        return TypeConverts.use(fieldType)
            .test(containsAny("char", "text").then(JavaType.STRING))
            .test(contains("number").then(DmTypeConvert::toNumberType))
            .test(containsAny("numeric", "dec", "money").then(JavaType.BIG_DECIMAL))
            .test(containsAny("bit", "bool").then(JavaType.BOOLEAN))
            .test(contains("bigint").then(JavaType.BIG_INTEGER))
            .test(containsAny("int", "byte").then(JavaType.INTEGER))
            .test(contains("binary").then(JavaType.BYTE_ARRAY))
            .test(contains("float").then(JavaType.FLOAT))
            .test(containsAny("double", "real").then(JavaType.DOUBLE))
            .test(containsAny("date", "time").then(JavaType.DATE))
            .test(contains("clob").then(JavaType.CLOB))
            .test(contains("blob").then(JavaType.BLOB))
            .test(contains("image").then(JavaType.BYTE_ARRAY))
            .or(JavaType.STRING);
    }

    private static DataType toNumberType(String typeName) {
        if (typeName.matches("number\\([0-9]\\)")) {
            return JavaType.INTEGER;
        } else if (typeName.matches("number\\(1[0-8]\\)")) {
            return JavaType.LONG;
        }
        return JavaType.BIG_DECIMAL;
    }
}
