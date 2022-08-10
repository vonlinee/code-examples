package io.maker.codegen.mbp.query;

import org.jetbrains.annotations.NotNull;

import io.maker.codegen.mbp.config.po.TableInfo;

import java.util.List;

/**
 * @author nieqiurong 2021/1/6.
 * @since 3.5.0
 */
public interface IDatabaseQuery {

    /**
     * 获取表信息
     *
     * @return 表信息
     */
    @NotNull
    List<TableInfo> queryTables();

}
