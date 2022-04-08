package org.setamv.shardingsphere.sample.dynamic.service;

import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import org.apache.commons.collections4.CollectionUtils;
import org.setamv.shardingsphere.sample.dynamic.dto.PurchaseOrderGetReqDTO;
import org.setamv.shardingsphere.sample.dynamic.dto.PurchaseOrderQueryReqDTO;
import org.setamv.shardingsphere.sample.dynamic.mapper.PurchaseOrderMapper;
import org.setamv.shardingsphere.sample.dynamic.model.PurchaseOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 采购单Service
 *
 * @author setamv
 * @date 2021-04-15
 */
@Service
public class PurchaseOrderService {

    @Autowired
    private PurchaseOrderMapper purchaseOrderMapper;

    @Autowired
    private PurchaseItemService purchaseItemService;

    /**
     * 新增采购单。如果采购货品明细{@link PurchaseOrder#getPurchaseItems()}不为空，同时新增采购货品明细
     * @param purchaseOrder 采购单
     * @return 采购单ID
     */
    @Transactional(rollbackFor = Exception.class)
    public Long add(PurchaseOrder purchaseOrder) {
        purchaseOrderMapper.insert(purchaseOrder);

        if (CollectionUtils.isNotEmpty(purchaseOrder.getPurchaseItems())) {
            purchaseItemService.batchAdd(purchaseOrder.getPurchaseItems());
        }
        return purchaseOrder.getOrderId();
    }

    /**
     * 获取采购单
     * @param reqDTO 请求参数
     * @return 采购单
     */
    public PurchaseOrder get(PurchaseOrderGetReqDTO reqDTO) {
        return purchaseOrderMapper.get(reqDTO);
    }

    /**
     * 查询采购单列表
     * @param queryParams
     * @return 采购单列表
     */
    public List<PurchaseOrder> query(PurchaseOrderQueryReqDTO queryParams) {
        return purchaseOrderMapper.query(queryParams);
    }

    /**
     * 分页查询采购单列表
     * @param queryParams
     * @return 采购单列表
     */
    public PageInfo<PurchaseOrder> queryPage(PurchaseOrderQueryReqDTO queryParams) {
        return PageMethod
                .startPage(queryParams.getPageNum(), queryParams.getPageSize())
                .doSelectPageInfo(() -> purchaseOrderMapper.query(queryParams));
    }
}
