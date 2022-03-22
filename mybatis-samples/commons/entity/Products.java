package mybatis.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 产品表
 * </p>
 *
 * @author someone
 * @since 2022-03-20
 */
@ApiModel(value = "Products对象", description = "产品表")
public class Products implements Serializable {

    private static final long serialVersionUID = 1L;

    private String prodId;

    private Integer vendId;

    private String prodName;

    private BigDecimal prodPrice;

    private String prodDesc;

    public String getProdId() {
        return prodId;
    }

    public void setProdId(String prodId) {
        this.prodId = prodId;
    }
    public Integer getVendId() {
        return vendId;
    }

    public void setVendId(Integer vendId) {
        this.vendId = vendId;
    }
    public String getProdName() {
        return prodName;
    }

    public void setProdName(String prodName) {
        this.prodName = prodName;
    }
    public BigDecimal getProdPrice() {
        return prodPrice;
    }

    public void setProdPrice(BigDecimal prodPrice) {
        this.prodPrice = prodPrice;
    }
    public String getProdDesc() {
        return prodDesc;
    }

    public void setProdDesc(String prodDesc) {
        this.prodDesc = prodDesc;
    }

    @Override
    public String toString() {
        return "Products{" +
            "prodId=" + prodId +
            ", vendId=" + vendId +
            ", prodName=" + prodName +
            ", prodPrice=" + prodPrice +
            ", prodDesc=" + prodDesc +
        "}";
    }
}
