package org.setamv.shardingsphere.sample.dynamic.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.Collection;

/**
 * 采购单查询参数DTO
 *
 * @author setamv
 * @date 2021-04-15
 */
@Data
public class PurchaseOrderQueryReqDTO extends BaseDTO {

    private Long orderId;
    private LocalDate orderDate;
    private String supplierName;
    private LocalDate startOrderDate;
    private LocalDate endOrderDate;
    private Collection<LocalDate> orderDates;
}
