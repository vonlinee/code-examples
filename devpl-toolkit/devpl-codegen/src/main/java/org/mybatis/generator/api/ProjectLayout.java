package org.mybatis.generator.api;

import java.io.File;
import java.util.Properties;

/**
 * 项目结构
 */
public interface ProjectLayout {

    void setProperties(Properties properties);

    /**
     * @param projectRootPath 项目所在根目录
     * @param relativePath    文件相对路径
     * @return 文件存放的目录
     */
    File locate(String projectRootPath, String relativePath);
}
