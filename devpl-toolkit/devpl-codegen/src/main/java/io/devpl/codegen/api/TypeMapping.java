package io.devpl.codegen.api;

import io.devpl.codegen.mbpg.config.ProjectConfiguration;
import io.devpl.codegen.mbpg.config.rules.DataType;

/**
 * 数据库字段类型转换
 */
public interface TypeMapping {

    /**
     * 执行类型转换
     * @param globalConfig 全局配置
     * @param fieldType    字段类型
     * @return ignore
     */
    DataType processTypeConvert(ProjectConfiguration globalConfig, String fieldType);
}
