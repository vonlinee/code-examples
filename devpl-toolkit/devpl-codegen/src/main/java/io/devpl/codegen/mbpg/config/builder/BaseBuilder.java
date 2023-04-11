package io.devpl.codegen.mbpg.config.builder;

import io.devpl.codegen.mbpg.config.Builder;
import io.devpl.codegen.mbpg.config.StrategyConfig;
import io.devpl.codegen.mbpg.template.impl.ControllerTemplateArguments;
import io.devpl.codegen.mbpg.template.impl.EntityTemplateArguments;
import io.devpl.codegen.mbpg.template.impl.MapperTemplateArguments;
import io.devpl.codegen.mbpg.template.impl.ServiceTemplateArguments;
import org.jetbrains.annotations.NotNull;

/**
 * 配置构建
 *
 * @author nieqiurong 2020/10/11.
 * @since 3.5.0
 */
public class BaseBuilder implements Builder<StrategyConfig> {

    private final StrategyConfig strategyConfig;

    public BaseBuilder(@NotNull StrategyConfig strategyConfig) {
        this.strategyConfig = strategyConfig;
    }

    @NotNull
    public EntityTemplateArguments.Builder entityBuilder() {
        return strategyConfig.entityBuilder();
    }

    @NotNull
    public ControllerTemplateArguments.Builder controllerBuilder() {
        return strategyConfig.controllerBuilder();
    }

    @NotNull
    public MapperTemplateArguments.Builder mapperBuilder() {
        return strategyConfig.mapperBuilder();
    }

    @NotNull
    public ServiceTemplateArguments.Builder serviceBuilder() {
        return strategyConfig.serviceBuilder();
    }

    @NotNull
    @Override
    public StrategyConfig build() {
        this.strategyConfig.validate();
        return this.strategyConfig;
    }
}
