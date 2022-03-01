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
package com.baomidou.mybatisplus.generator.jdbc;

import org.apache.ibatis.type.JdbcType;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author nieqiurong 2021/2/8.
 * @since 3.5.0
 */
public class DatabaseMetadataLoader {

    private final DatabaseMetaData databaseMetaData;

    public DatabaseMetadataLoader(Connection connection) throws SQLException {
        this.databaseMetaData = connection.getMetaData();
    }

    /**
     * 获取表字段信息
     *
     * @return 表字段信息 (小写字段名->字段信息)
     */
    public Map<String, ColumnInfo> getColumnsInfo(String catalog, String schemaPattern, String tableNamePattern) throws SQLException {
        ResultSet resultSet = databaseMetaData.getColumns(catalog, schemaPattern, tableNamePattern, "%");
        Map<String, ColumnInfo> columnsInfoMap = new HashMap<>();
        while (resultSet.next()) {
            ColumnInfo columnInfo = new ColumnInfo();
            columnInfo.setName(resultSet.getString("COLUMN_NAME"));
            columnInfo.setScale(resultSet.getInt("DECIMAL_DIGITS"));
            columnInfo.setJdbcType(JdbcType.forCode(resultSet.getInt("DATA_TYPE")));
            columnInfo.setLength(resultSet.getInt("COLUMN_SIZE"));
            columnInfo.setRemarks(resultSet.getString("REMARKS"));
            columnInfo.setDefaultValue(resultSet.getString("COLUMN_DEF"));
            columnInfo.setNullable(resultSet.getInt("NULLABLE") == DatabaseMetaData.columnNullable);
            columnsInfoMap.put(columnInfo.getName().toLowerCase(), columnInfo);
        }
        return Collections.unmodifiableMap(columnsInfoMap);
    }
}
