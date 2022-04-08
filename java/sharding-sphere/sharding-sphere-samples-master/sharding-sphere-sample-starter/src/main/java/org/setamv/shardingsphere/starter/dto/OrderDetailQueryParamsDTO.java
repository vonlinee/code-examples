package org.setamv.shardingsphere.starter.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.Collection;

@Data
public class OrderDetailQueryParamsDTO {
    private Long orderId;
    private Collection<Long> orderIds;
    private Long productId;

    /**
     * 下面按主表的订单日期过滤
      */
    private LocalDate mainOrderDate;
    private LocalDate startMainOrderDate;
    private LocalDate endMainOrderDate;

    /**
     * 下面是按明细表的订单日期过滤
     */
    private LocalDate detailOrderDate;
    private LocalDate startDetailOrderDate;
    private LocalDate endDetailOrderDate;
}
