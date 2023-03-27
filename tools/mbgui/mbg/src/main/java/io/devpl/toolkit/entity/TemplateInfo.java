package io.devpl.toolkit.entity;

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
    private String tmplId;

    /**
     * 模板名称
     */
    private String tmplName;

    /**
     * 模板所在路径
     */
    private String tmplPath;

    /**
     * 是否内置，内置模板不可更改
     */
    private boolean builtin;
}
