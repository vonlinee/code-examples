package io.maker.codegen.mbp.config.builder;

import org.jetbrains.annotations.NotNull;

import io.maker.codegen.mbp.config.IConfigBuilder;
import io.maker.codegen.mbp.config.StrategyConfig;

/**
 * 配置构建
 */
public class BaseBuilder implements IConfigBuilder<StrategyConfig> {

    private final StrategyConfig strategyConfig;

    public BaseBuilder(@NotNull StrategyConfig strategyConfig) {
        this.strategyConfig = strategyConfig;
    }

    @NotNull
    public Entity.Builder entityBuilder() {
        return strategyConfig.entityBuilder();
    }

    @NotNull
    public Controller.Builder controllerBuilder() {
        return strategyConfig.controllerBuilder();
    }

    @NotNull
    public Mapper.Builder mapperBuilder() {
        return strategyConfig.mapperBuilder();
    }

    @NotNull
    public Service.Builder serviceBuilder() {
        return strategyConfig.serviceBuilder();
    }

    @NotNull
    @Override
    public StrategyConfig build() {
        this.strategyConfig.validate();
        return this.strategyConfig;
    }
}
