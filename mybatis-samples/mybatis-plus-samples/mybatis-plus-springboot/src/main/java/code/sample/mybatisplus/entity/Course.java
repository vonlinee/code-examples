package code.sample.mybatisplus.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 *
 * </p>
 * @author ly-busicen
 * @since 2022-02-22
 */
@ApiModel(value = "Course对象", description = "")
@TableName(value = "course")
public class Course implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer cid;

    private String cname;

    private Integer tid;

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public Integer getTid() {
        return tid;
    }

    public void setTid(Integer tid) {
        this.tid = tid;
    }

    @Override
    public String toString() {
        return "Course{" +
                "cid=" + cid +
                ", cname=" + cname +
                ", tid=" + tid +
                "}";
    }
}
