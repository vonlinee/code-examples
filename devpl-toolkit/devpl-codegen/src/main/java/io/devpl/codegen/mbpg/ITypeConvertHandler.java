package io.devpl.codegen.mbpg;

import io.devpl.codegen.api.TypeRegistry;
import io.devpl.codegen.mbpg.config.ProjectConfiguration;
import io.devpl.codegen.jdbc.MetaInfo;
import io.devpl.codegen.mbpg.config.rules.DataType;
import org.jetbrains.annotations.NotNull;

/**
 * 类型转换处理器
 */
public interface ITypeConvertHandler {

    /**
     * 转换字段类型
     * @param globalConfig 全局配置
     * @param typeRegistry 类型注册信息
     * @param metaInfo     字段元数据信息
     * @return 子类类型
     */
    @NotNull
    DataType convert(ProjectConfiguration globalConfig, TypeRegistry typeRegistry, MetaInfo metaInfo);
}
