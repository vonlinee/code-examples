package io.devpl.tookit.fxui.model;

import lombok.Data;

/**
 * 除了数据库配置外的配置:通用配置项
 */
@Data
public class CodeGenConfiguration {

    /**
     * 本配置的名称: 可作为唯一ID
     */
    private String name;

    private String connectorJarPath;

    /**
     * 项目所在根路径
     */
    private String projectFolder;

    /**
     * 父包名
     */
    private String parentPackage;

    /**
     * 实体类所在包名
     */
    private String modelPackage;

    /**
     * 实体类存放目录
     */
    private String modelPackageTargetFolder;

    /**
     * Mapper接口包名
     */
    private String daoPackage;

    /**
     * Mapper接口存放目录
     */
    private String daoTargetFolder;

    /**
     * 映射XML文件包名
     */
    private String mappingXMLPackage;

    /**
     * 映射XML文件存放目录
     */
    private String mappingXMLTargetFolder;
}
