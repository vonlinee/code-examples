package io.devpl.codegen.mbpg.query;

import io.devpl.codegen.mbpg.config.po.TableInfo;

import java.util.List;

public interface DatabaseIntrospector {

    /**
     * 获取表信息
     *
     * @return 表信息
     */
    List<TableInfo> introspecTables();
}
