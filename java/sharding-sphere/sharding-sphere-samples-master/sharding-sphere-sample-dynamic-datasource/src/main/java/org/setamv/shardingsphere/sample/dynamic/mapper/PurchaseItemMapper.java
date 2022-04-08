package org.setamv.shardingsphere.sample.dynamic.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.setamv.shardingsphere.sample.dynamic.dto.PurchaseItemDTO;
import org.setamv.shardingsphere.sample.dynamic.dto.PurchaseItemQueryReqDTO;
import org.setamv.shardingsphere.sample.dynamic.model.PurchaseItem;

import java.util.Collection;
import java.util.List;

/**
 * 采购货品明细Mapper
 *
 * @author setamv
 * @date 2021-04-15
 */
@Mapper
public interface PurchaseItemMapper {

    /**
     * 批量插入采购货品明细
     * @param purchaseItems 采购货品明细清单
     * @return int
     */
    int batchInsert(Collection<PurchaseItem> purchaseItems);

    /**
     * 更新采购货品明细
     * @param purchaseItem 采购货品明细
     * @return int
     */
    int update(PurchaseItem purchaseItem);

    /**
     * 查询采购货品明细列表
     * @param queryParams 查询参数
     * @return 采购货品明细列表
     */
    List<PurchaseItem> query(PurchaseItemQueryReqDTO queryParams);

    /**
     * 查询采购货品明细（包含采购单的信息）列表
     * @param queryParams 查询参数
     * @return 采购货品明细（包含采购单的信息）列表
     */
    List<PurchaseItemDTO> queryWithOrder(PurchaseItemQueryReqDTO queryParams);

}
