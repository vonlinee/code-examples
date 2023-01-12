package org.mybatis.generator.api;

import java.io.File;
import java.util.Properties;

public class MavenProjectLayout implements ProjectLayout {

    private Properties properties;

    @Override
    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    @Override
    public File locate(String projectRootPath, String relativePath) {
        File fileLocation;
        if (relativePath.endsWith(".java")) {
            fileLocation = new File(projectRootPath + "src/main/java" + relativePath);
        } else {
            // 资源文件
            fileLocation = new File(projectRootPath + "src/main/resources" + relativePath);
        }
        return fileLocation;
    }
}
