package org.setamv.shardingsphere.sample.dynamic.controller;

import com.github.pagehelper.PageInfo;
import org.setamv.shardingsphere.sample.dynamic.dto.PurchaseItemDTO;
import org.setamv.shardingsphere.sample.dynamic.dto.PurchaseItemQueryReqDTO;
import org.setamv.shardingsphere.sample.dynamic.dto.PurchaseOrderGetReqDTO;
import org.setamv.shardingsphere.sample.dynamic.dto.PurchaseOrderQueryReqDTO;
import org.setamv.shardingsphere.sample.dynamic.model.PurchaseItem;
import org.setamv.shardingsphere.sample.dynamic.model.PurchaseOrder;
import org.setamv.shardingsphere.sample.dynamic.service.PurchaseItemService;
import org.setamv.shardingsphere.sample.dynamic.service.PurchaseOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;

/**
 * 采购单Controller
 *
 * @author setamv
 * @date 2021-04-15
 */
@RestController
@RequestMapping("/purchase")
public class PurchaseController {

    @Autowired
    private PurchaseOrderService purchaseOrderService;

    @Autowired
    private PurchaseItemService purchaseItemService;

    /**
     * 新增采购单。可以附带采购获评明细清单
     * @param purchaseOrder 采购单
     * @return 响应结果
     */
    @PostMapping(value = "/addOrder", produces = MediaType.APPLICATION_JSON_VALUE)
    public Object addOrder(@RequestBody PurchaseOrder purchaseOrder) {
        return purchaseOrderService.add(purchaseOrder);
    }

    /**
     * 获取采购单
     * @param reqDTO 请求参数
     * @return 采购单
     */
    @PostMapping(value = "/getOrder", produces = MediaType.APPLICATION_JSON_VALUE)
    public PurchaseOrder getOrder(@RequestBody PurchaseOrderGetReqDTO reqDTO) {
        return purchaseOrderService.get(reqDTO);
    }

    /**
     * 查询采购单
     * @param reqDTO 请求参数
     * @return 采购单
     */
    @PostMapping(value = "/queryOrder", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<PurchaseOrder> queryOrder(@RequestBody PurchaseOrderQueryReqDTO reqDTO) {
        return purchaseOrderService.query(reqDTO);
    }

    /**
     * 分页查询采购单
     * @param reqDTO 请求参数
     * @return 采购单
     */
    @PostMapping(value = "/queryOrderPage", produces = MediaType.APPLICATION_JSON_VALUE)
    public PageInfo<PurchaseOrder> getOrder(@RequestBody PurchaseOrderQueryReqDTO reqDTO) {
        return purchaseOrderService.queryPage(reqDTO);
    }

    /**
     * 新增采购货品明细
     * @param purchaseItems 采购货品明细
     * @return 响应结果
     */
    @PostMapping(value = "/addItems", produces = MediaType.APPLICATION_JSON_VALUE)
    public Object addItems(@RequestBody Collection<PurchaseItem> purchaseItems) {
        purchaseItemService.batchAdd(purchaseItems);
        return purchaseItems;
    }

    /**
     * 查询采购货品明细列表
     * @param queryParams 查询参数
     * @return 采购货品明细列表
     */
    @PostMapping(value = "/queryItems", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<PurchaseItem> queryItems(@RequestBody PurchaseItemQueryReqDTO queryParams) {
        return purchaseItemService.query(queryParams);
    }

    /**
     * 查询采购货品明细（包含采购单的信息）列表
     * @param queryParams 查询参数
     * @return 采购货品明细（包含采购单的信息）列表
     */
    @PostMapping(value = "/queryItemsWithOrder", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<PurchaseItemDTO> queryItemsWithOrder(@RequestBody PurchaseItemQueryReqDTO queryParams) {
        return purchaseItemService.queryWithOrder(queryParams);
    }
}
