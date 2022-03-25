package multidatasource.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

/**
 * <p>
 * 生产商表
 * </p>
 *
 * @author someone
 * @since 2022-03-20
 */
public class Vendor implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "vend_id", type = IdType.AUTO)
    private Integer vendId;

    private String vendName;

    private String vendAddress;

    private String vendCity;

    private String vendState;

    private String vendZip;

    private String vendCountry;

    public Integer getVendId() {
        return vendId;
    }

    public void setVendId(Integer vendId) {
        this.vendId = vendId;
    }
    public String getVendName() {
        return vendName;
    }

    public void setVendName(String vendName) {
        this.vendName = vendName;
    }
    public String getVendAddress() {
        return vendAddress;
    }

    public void setVendAddress(String vendAddress) {
        this.vendAddress = vendAddress;
    }
    public String getVendCity() {
        return vendCity;
    }

    public void setVendCity(String vendCity) {
        this.vendCity = vendCity;
    }
    public String getVendState() {
        return vendState;
    }

    public void setVendState(String vendState) {
        this.vendState = vendState;
    }
    public String getVendZip() {
        return vendZip;
    }

    public void setVendZip(String vendZip) {
        this.vendZip = vendZip;
    }
    public String getVendCountry() {
        return vendCountry;
    }

    public void setVendCountry(String vendCountry) {
        this.vendCountry = vendCountry;
    }

    @Override
    public String toString() {
        return "Vendors{" +
            "vendId=" + vendId +
            ", vendName=" + vendName +
            ", vendAddress=" + vendAddress +
            ", vendCity=" + vendCity +
            ", vendState=" + vendState +
            ", vendZip=" + vendZip +
            ", vendCountry=" + vendCountry +
        "}";
    }
}
