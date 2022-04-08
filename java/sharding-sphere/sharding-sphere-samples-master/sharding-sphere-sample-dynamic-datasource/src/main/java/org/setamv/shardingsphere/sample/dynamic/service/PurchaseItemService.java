package org.setamv.shardingsphere.sample.dynamic.service;

import org.apache.commons.collections4.CollectionUtils;
import org.setamv.shardingsphere.sample.dynamic.dto.PurchaseItemDTO;
import org.setamv.shardingsphere.sample.dynamic.dto.PurchaseItemQueryReqDTO;
import org.setamv.shardingsphere.sample.dynamic.mapper.PurchaseItemMapper;
import org.setamv.shardingsphere.sample.dynamic.model.PurchaseItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

/**
 * 采购货品明细Service
 *
 * @author setamv
 * @date 2021-04-15
 */
@Service
public class PurchaseItemService {

    @Autowired
    private PurchaseItemMapper purchaseItemMapper;

    /**
     * 新增采购货品明细
     * @param purchaseItems 采购货品明细清单
     */
    @Transactional(rollbackFor = Exception.class)
    public void batchAdd(Collection<PurchaseItem> purchaseItems) {
        if (CollectionUtils.isEmpty(purchaseItems)) {
            return;
        }
        purchaseItemMapper.batchInsert(purchaseItems);
    }

    /**
     * 查询采购货品明细列表
     * @param queryParams 查询参数
     * @return 采购货品明细列表
     */
    public List<PurchaseItem> query(PurchaseItemQueryReqDTO queryParams) {
        return purchaseItemMapper.query(queryParams);
    }

    /**
     * 查询采购货品明细（包含采购单的信息）列表
     * @param queryParams 查询参数
     * @return 采购货品明细（包含采购单的信息）列表
     */
    public List<PurchaseItemDTO> queryWithOrder(PurchaseItemQueryReqDTO queryParams) {
        return purchaseItemMapper.queryWithOrder(queryParams);
    }
}
