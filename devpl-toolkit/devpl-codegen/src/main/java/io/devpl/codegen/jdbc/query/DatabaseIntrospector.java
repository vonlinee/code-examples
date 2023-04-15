package io.devpl.codegen.jdbc.query;

import io.devpl.codegen.api.IntrospectedTable;

import java.sql.Connection;
import java.util.List;

public interface DatabaseIntrospector {

    /**
     * 获取所有的表信息
     * @return 表信息
     */
    List<IntrospectedTable> introspecTables();

    void convertTableFields(IntrospectedTable tableInfo);
}
