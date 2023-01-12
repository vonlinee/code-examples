package io.devpl.codegen.mbpg.config.builder;

import io.devpl.codegen.mbpg.config.*;

/**
 * 生成器 Builder
 */
public class GeneratorBuilder {

    /**
     * 全局配置
     * @return GlobalConfig
     */
    public static GlobalConfig globalConfig() {
        return new GlobalConfig.Builder().build();
    }

    /**
     * 全局配置 Builder
     * @return GlobalConfig.Builder
     */
    public static GlobalConfig.Builder globalConfigBuilder() {
        return new GlobalConfig.Builder();
    }

    /**
     * 包相关的配置项
     * @return PackageConfig
     */
    public static PackageConfig packageConfig() {
        return new PackageConfig.Builder().build();
    }

    /**
     * 包相关的配置项 Builder
     * @return PackageConfig.Builder
     */
    public static PackageConfig.Builder packageConfigBuilder() {
        return new PackageConfig.Builder();
    }

    /**
     * 策略配置项
     * @return StrategyConfig
     */
    public static StrategyConfig strategyConfig() {
        return new StrategyConfig.Builder().build();
    }

    /**
     * 策略配置项 Builder
     * @return StrategyConfig.Builder
     */
    public static StrategyConfig.Builder strategyConfigBuilder() {
        return new StrategyConfig.Builder();
    }

    /**
     * 模板路径配置项
     * @return TemplateConfig
     */
    public static TemplateConfig templateConfig() {
        return new TemplateConfig.Builder().build();
    }

    /**
     * 模板路径配置项 Builder
     * @return TemplateConfig.Builder
     */
    public static TemplateConfig.Builder templateConfigBuilder() {
        return new TemplateConfig.Builder();
    }

    /**
     * 注入配置项
     * @return InjectionConfig
     */
    public static InjectionConfig injectionConfig() {
        return new InjectionConfig.Builder().build();
    }

    /**
     * 注入配置项 Builder
     * @return InjectionConfig.Builder
     */
    public static InjectionConfig.Builder injectionConfigBuilder() {
        return new InjectionConfig.Builder();
    }
}
