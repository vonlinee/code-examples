package org.mybatis.generator.internal.util;

public class RuntimeUtils {

    /**
     * 获取运行时Java版本
     * @return
     */
    public static JavaVersion getJvmVersion() {
        String specVersion = System.getProperty("java.specification.version");
        return JavaVersion.getJavaVersion(specVersion);
    }
}
