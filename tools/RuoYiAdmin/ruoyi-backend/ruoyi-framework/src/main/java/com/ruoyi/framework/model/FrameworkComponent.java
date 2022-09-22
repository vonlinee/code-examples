package com.ruoyi.framework.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 框架的组件扩展类，通常是继承了某个框架的接口或抽象类
 */
@Data
@Table(name = "t_framework_component")
public class FrameworkComponent {

    @Id
    @Column(name = "COMPONENT_ID")
    private Integer componentId;

    @Column(name = "IS_ENABLE")
    private Boolean isEnable;

    @Column(name = "CLASS_NAME")
    private String className;

    @Column(name = "COMPONENT_CLASS_NAME")
    private String componentClassName;

    /**
     * 排序号，比如多个Filter的排序
     */
    @Column(name = "ORDER_NUM")
    private Integer orderNum;
}
