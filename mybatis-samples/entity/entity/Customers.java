package mybatis.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 客户表
 * </p>
 *
 * @author someone
 * @since 2022-03-20
 */
@ApiModel(value = "Customers对象", description = "客户表")
public class Customers implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "cust_id", type = IdType.AUTO)
    private Integer custId;

    private String custName;

    private String custAddress;

    private String custCity;

    private String custState;

    private String custZip;

    private String custCountry;

    private String custContact;

    private String custEmail;

    public Integer getCustId() {
        return custId;
    }

    public void setCustId(Integer custId) {
        this.custId = custId;
    }
    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }
    public String getCustAddress() {
        return custAddress;
    }

    public void setCustAddress(String custAddress) {
        this.custAddress = custAddress;
    }
    public String getCustCity() {
        return custCity;
    }

    public void setCustCity(String custCity) {
        this.custCity = custCity;
    }
    public String getCustState() {
        return custState;
    }

    public void setCustState(String custState) {
        this.custState = custState;
    }
    public String getCustZip() {
        return custZip;
    }

    public void setCustZip(String custZip) {
        this.custZip = custZip;
    }
    public String getCustCountry() {
        return custCountry;
    }

    public void setCustCountry(String custCountry) {
        this.custCountry = custCountry;
    }
    public String getCustContact() {
        return custContact;
    }

    public void setCustContact(String custContact) {
        this.custContact = custContact;
    }
    public String getCustEmail() {
        return custEmail;
    }

    public void setCustEmail(String custEmail) {
        this.custEmail = custEmail;
    }

    @Override
    public String toString() {
        return "Customers{" +
            "custId=" + custId +
            ", custName=" + custName +
            ", custAddress=" + custAddress +
            ", custCity=" + custCity +
            ", custState=" + custState +
            ", custZip=" + custZip +
            ", custCountry=" + custCountry +
            ", custContact=" + custContact +
            ", custEmail=" + custEmail +
        "}";
    }
}
