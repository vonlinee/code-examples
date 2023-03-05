package org.example.mybatisplus.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * </p>
 * @author ly-busicen
 * @since 2022-02-22
 */
@Data
@ApiModel(value = "Course对象", description = "")
@TableName(value = "course")
public class Course implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer cid;

    private String cname;

    private Integer tid;
}
