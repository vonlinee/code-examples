package sample.spring.integration.mybatis.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 订单表
 * </p>
 *
 * @author someone
 * @since 2022-03-20
 */
public class Orders implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer orderNum;

    private LocalDateTime orderDate;

    private Integer custId;

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }
    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }
    public Integer getCustId() {
        return custId;
    }

    public void setCustId(Integer custId) {
        this.custId = custId;
    }

    @Override
    public String toString() {
        return "Orders{" +
            "orderNum=" + orderNum +
            ", orderDate=" + orderDate +
            ", custId=" + custId +
        "}";
    }
}
