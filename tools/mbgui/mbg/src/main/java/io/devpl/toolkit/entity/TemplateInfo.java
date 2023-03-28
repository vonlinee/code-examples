package io.devpl.toolkit.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

/**
 * 模板信息
 */
@Data
public class TemplateInfo implements Serializable {

    /**
     * 模板ID
     */
    @TableId(value = "template_id", type = IdType.ASSIGN_UUID)
    private String templateId;

    /**
     * 模板名称
     */
    @TableField(value = "template_name")
    private String templateName;

    /**
     * 模板所在路径
     */
    @TableField(value = "template_path")
    private String templatePath;

    /**
     * 是否内置，内置模板不可更改
     */
    @TableField(value = "builtin")
    private boolean builtin;

    /**
     * 备注信息
     */
    @TableField(value = "remark")
    private String remark;
}
