package io.devpl.codegen.api;

import io.devpl.codegen.mbpg.config.StrategyConfig;
import io.devpl.codegen.mbpg.config.rules.NamingStrategy;
import io.devpl.codegen.utils.StringUtils;

import java.util.Set;

public class DefaultNameConvert implements INameConvert {

    private final StrategyConfig strategyConfig;

    public DefaultNameConvert(StrategyConfig strategyConfig) {
        this.strategyConfig = strategyConfig;
    }

    @Override
    public String entityNameConvert(IntrospectedTable tableInfo) {
        return NamingStrategy.capitalFirst(processName(tableInfo.getName(), strategyConfig.entityArguments()
                .getNamingStrategy(), strategyConfig.getTablePrefix(), strategyConfig.getTableSuffix()));
    }

    @Override
    public String propertyNameConvert(String fieldName) {
        return processName(fieldName, strategyConfig.entityArguments()
                .getColumnNaming(), strategyConfig.getFieldPrefix(), strategyConfig.getFieldSuffix());
    }

    private String processName(String name, NamingStrategy strategy, Set<String> prefix, Set<String> suffix) {
        String propertyName = name;
        // 删除前缀
        if (prefix.size() > 0) {
            propertyName = NamingStrategy.removePrefix(propertyName, prefix);
        }
        // 删除后缀
        if (suffix.size() > 0) {
            propertyName = NamingStrategy.removeSuffix(propertyName, suffix);
        }
        if (StringUtils.isBlank(propertyName)) {
            throw new RuntimeException(String.format("%s 的名称转换结果为空，请检查是否配置问题", name));
        }
        // 下划线转驼峰
        if (NamingStrategy.UNDERLINE_TO_CAMEL.equals(strategy)) {
            return NamingStrategy.underlineToCamel(propertyName);
        }
        return propertyName;
    }
}