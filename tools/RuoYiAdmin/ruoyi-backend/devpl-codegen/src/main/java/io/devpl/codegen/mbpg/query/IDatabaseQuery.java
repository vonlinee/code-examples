package io.devpl.codegen.mbpg.query;

import io.devpl.codegen.mbpg.config.po.TableInfo;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface IDatabaseQuery {

    /**
     * 获取表信息
     * @return 表信息
     */
    @NotNull
    List<TableInfo> queryTables();
}
