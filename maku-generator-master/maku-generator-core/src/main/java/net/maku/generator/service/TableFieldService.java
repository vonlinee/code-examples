package net.maku.generator.service;

import net.maku.generator.common.service.BaseService;
import net.maku.generator.entity.TableFieldInfo;

import java.util.List;

/**
 * 表字段
 *
 * @author 阿沐 babamu@126.com
 * <a href="https://maku.net">MAKU</a>
 */
public interface TableFieldService extends BaseService<TableFieldInfo> {

    List<TableFieldInfo> getByTableId(Long tableId);

    void deleteBatchTableIds(Long[] tableIds);

    /**
     * 修改表字段数据
     *
     * @param tableId        表ID
     * @param tableFieldList 字段列表
     */
    void updateTableField(Long tableId, List<TableFieldInfo> tableFieldList);

    /**
     * 初始化字段数据
     */
    void initFieldList(List<TableFieldInfo> tableFieldList);
}