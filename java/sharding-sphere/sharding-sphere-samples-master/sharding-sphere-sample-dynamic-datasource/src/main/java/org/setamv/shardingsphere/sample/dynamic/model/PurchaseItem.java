package org.setamv.shardingsphere.sample.dynamic.model;

import lombok.Data;

import java.time.LocalDate;

/**
 * 租户
 *
 * @author setamv
 * @date 2021-04-15
 */
@Data
public class PurchaseItem extends BaseEntity {

    private Long itemId;
    private Long orderId;
    private LocalDate orderDate;
    private Long productId;
    private Float quantity;
    private Float price;
}
