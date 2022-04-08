package org.setamv.shardingsphere.sample.dynamic.model;

import lombok.Data;

import java.time.LocalDate;
import java.util.Collection;

/**
 * 租户
 *
 * @author setamv
 * @date 2021-04-15
 */
@Data
public class PurchaseOrder extends BaseEntity {

    private Long orderId;
    private LocalDate orderDate;
    private String supplierName;
    private Float amount;
    private Long purchaserId;

    private Collection<PurchaseItem> purchaseItems;
}
