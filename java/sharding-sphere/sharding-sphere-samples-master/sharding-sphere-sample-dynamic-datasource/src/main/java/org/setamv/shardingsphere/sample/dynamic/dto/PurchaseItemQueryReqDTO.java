package org.setamv.shardingsphere.sample.dynamic.dto;

import lombok.Data;
import org.setamv.shardingsphere.sample.dynamic.model.PurchaseItem;

import java.time.LocalDate;

/**
 * 采购货品明细查询参数DTO
 *
 * @author setamv
 * @date 2021-04-15
 */
@Data
public class PurchaseItemQueryReqDTO extends PurchaseItem {

    private LocalDate itemOrderDate;
    private LocalDate startItemOrderDate;
    private LocalDate endItemOrderDate;

    /**
     * 采购单的订单日期查询条件。（用于限定采购单主表的订单日期条件）
     */
    private LocalDate startOrderDate;
    private LocalDate endOrderDate;
}
