package org.setamv.shardingsphere.sample.dynamic.dto;

import lombok.Data;
import org.setamv.shardingsphere.sample.dynamic.model.PurchaseItem;

/**
 * 采购货品明细DTO
 *
 * @author setamv
 * @date 2021-04-16
 */
@Data
public class PurchaseItemDTO extends PurchaseItem {

    private String supplierName;
    private String purchaserId;
}
