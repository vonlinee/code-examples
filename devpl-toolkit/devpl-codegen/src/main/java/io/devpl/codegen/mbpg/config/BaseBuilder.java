package io.devpl.codegen.mbpg.config;

import io.devpl.codegen.mbpg.template.impl.ControllerTemplateArguments;
import io.devpl.codegen.mbpg.template.impl.EntityTemplateArguments;
import io.devpl.codegen.mbpg.template.impl.MapperTemplateArguments;
import io.devpl.codegen.mbpg.template.impl.ServiceTemplateArguments;

/**
 * 配置构建
 */
public class BaseBuilder {

    private final StrategyConfig strategyConfig;

    public BaseBuilder(StrategyConfig strategyConfig) {
        this.strategyConfig = strategyConfig;
    }

    public EntityTemplateArguments.Builder entityBuilder() {
        return strategyConfig.entityBuilder();
    }

    public ControllerTemplateArguments.Builder controllerBuilder() {
        return strategyConfig.controllerBuilder();
    }

    public MapperTemplateArguments.Builder mapperBuilder() {
        return strategyConfig.mapperBuilder();
    }

    public ServiceTemplateArguments.Builder serviceBuilder() {
        return strategyConfig.serviceBuilder();
    }

    public StrategyConfig build() {
        this.strategyConfig.validate();
        return this.strategyConfig;
    }
}
