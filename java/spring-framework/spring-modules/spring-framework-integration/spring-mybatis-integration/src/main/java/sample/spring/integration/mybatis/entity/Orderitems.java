package sample.spring.integration.mybatis.entity;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 订单详情表
 * </p>
 *
 * @author someone
 * @since 2022-03-20
 */
public class Orderitems implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer orderNum;

    private Integer orderItem;

    private String prodId;

    private Integer quantity;

    private BigDecimal itemPrice;

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }
    public Integer getOrderItem() {
        return orderItem;
    }

    public void setOrderItem(Integer orderItem) {
        this.orderItem = orderItem;
    }
    public String getProdId() {
        return prodId;
    }

    public void setProdId(String prodId) {
        this.prodId = prodId;
    }
    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
    public BigDecimal getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(BigDecimal itemPrice) {
        this.itemPrice = itemPrice;
    }

    @Override
    public String toString() {
        return "Orderitems{" +
            "orderNum=" + orderNum +
            ", orderItem=" + orderItem +
            ", prodId=" + prodId +
            ", quantity=" + quantity +
            ", itemPrice=" + itemPrice +
        "}";
    }
}
