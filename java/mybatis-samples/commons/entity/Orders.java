package mybatis.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 订单表
 * </p>
 *
 * @author someone
 * @since 2022-03-20
 */
@ApiModel(value = "Orders对象", description = "订单表")
public class Orders implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("订单号")
    @TableId(value = "order_num", type = IdType.AUTO)
    private Integer orderNum;

    @ApiModelProperty("订单创建日期")
    private LocalDateTime orderDate;

    @ApiModelProperty("客户ID")
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
