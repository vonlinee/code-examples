package org.setamv.shardingsphere.sample.dynamic.dto;

import lombok.Data;

import java.time.LocalDate;

/**
 * 获取指定采购单请求参数DTO
 *
 * @author setamv
 * @date 2021-04-16
 */
@Data
public class PurchaseOrderGetReqDTO extends BaseDTO {

    private Long orderId;
    private LocalDate orderDate;
}
