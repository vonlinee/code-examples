package io.devpl.tookit.fxui.model;

import java.util.List;

import lombok.Data;

/**
 * 项目配置
 */
@Data
public class ProjectConfiguration {

    /**
     * 配置名称
     */
    private String name;

    /**
     * 项目所在根目录
     */
    private String projectRootFolder;

    /**
     * 父包名
     */
    private String parentPackage;

    /**
     * 实体类所在包名
     */
    private String entityPackageName;

    /**
     * 实体类存放目录：相对目录
     */
    private String entityPackageFolder;

    /**
     * Mapper接口包名
     */
    private String mapperPackageName;

    /**
     * Mapper接口存放目录
     */
    private String mapperFolder;

    /**
     * 映射XML文件包名
     */
    private String mapperXmlPackage;

    /**
     * 映射XML文件存放目录
     */
    private String mapperXmlFolder;

    /**
     * 作者信息
     */
    private List<AuthorInfo> authors;
}
