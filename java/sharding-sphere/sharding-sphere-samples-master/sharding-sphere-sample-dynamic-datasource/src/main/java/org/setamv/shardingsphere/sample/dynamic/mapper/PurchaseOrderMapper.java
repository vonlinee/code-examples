package org.setamv.shardingsphere.sample.dynamic.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.setamv.shardingsphere.sample.dynamic.dto.PurchaseOrderGetReqDTO;
import org.setamv.shardingsphere.sample.dynamic.dto.PurchaseOrderQueryReqDTO;
import org.setamv.shardingsphere.sample.dynamic.model.PurchaseOrder;

import java.util.List;

/**
 * 采购单Mapper
 *
 * @author setamv
 * @date 2021-04-15
 */
@Mapper
public interface PurchaseOrderMapper {


    /**
     * 新增采购单
     * @param purchaseOrder 采购单
     * @return int
     */
    int insert(PurchaseOrder purchaseOrder);

    /**
     * 更新采购单
     * @param purchaseOrder 采购单
     * @return int
     */
    int update(PurchaseOrder purchaseOrder);

    /**
     * 获取采购单
     * @param reqDTO 请求参数
     * @return 采购单
     */
    PurchaseOrder get(PurchaseOrderGetReqDTO reqDTO);

    /**
     * 查询采购单列表
     * @param queryParams 请求参数
     * @return 采购单列表
     */
    List<PurchaseOrder> query(PurchaseOrderQueryReqDTO queryParams);
}
