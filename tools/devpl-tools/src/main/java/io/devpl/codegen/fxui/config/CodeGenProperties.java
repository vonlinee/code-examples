package io.devpl.codegen.fxui.config;

import lombok.Data;

/**
 * 生成器的配置属性
 */
@Data
public class CodeGenProperties {

    private String javaFileEncoding = "UTF-8";

    /**
     * Mapper 接口命名后缀
     */
    private String mapperNameSuffix;

}
