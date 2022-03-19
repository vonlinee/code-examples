package mybatis.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 订单详情表
 * </p>
 *
 * @author someone
 * @since 2022-03-20
 */
@ApiModel(value = "Orderitems对象", description = "订单详情表")
public class Orderitems implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("订单号")
    private Integer orderNum;

    @ApiModelProperty("订单物品号，在订单中的顺序")
    private Integer orderItem;

    @ApiModelProperty("产品ID，外键关联产品表主键")
    private String prodId;

    @ApiModelProperty("物品数量")
    private Integer quantity;

    @ApiModelProperty("物品价格")
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
