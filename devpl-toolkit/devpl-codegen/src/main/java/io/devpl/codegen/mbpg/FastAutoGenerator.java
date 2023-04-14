package io.devpl.codegen.mbpg;

import io.devpl.codegen.mbpg.config.*;
import io.devpl.codegen.generator.template.AbstractTemplateEngine;
import io.devpl.sdk.util.StringUtils;
import org.jetbrains.annotations.NotNull;

import java.util.Scanner;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

public final class FastAutoGenerator {

    /**
     * 数据源配置 Builder
     */
    private final DataSourceConfig.Builder dataSourceConfigBuilder;

    /**
     * 全局配置 Builder
     */
    private final ProjectConfiguration.Builder globalConfigBuilder;

    /**
     * 包配置 Builder
     */
    private final PackageConfiguration.Builder packageConfigBuilder;

    /**
     * 策略配置 Builder
     */
    private final StrategyConfig.Builder strategyConfigBuilder;

    /**
     * 注入配置 Builder
     */
    private final InjectionConfig.Builder injectionConfigBuilder;

    /**
     * 模板配置 Builder
     */
    private final TemplateConfiguration.Builder templateConfigBuilder;

    /**
     * 模板引擎
     */
    private AbstractTemplateEngine templateEngine;

    private FastAutoGenerator(DataSourceConfig.Builder dataSourceConfigBuilder) {
        this.dataSourceConfigBuilder = dataSourceConfigBuilder;
        this.globalConfigBuilder = new ProjectConfiguration.Builder();
        this.packageConfigBuilder = new PackageConfiguration.Builder();
        this.strategyConfigBuilder = new StrategyConfig.Builder();
        this.injectionConfigBuilder = new InjectionConfig.Builder();
        this.templateConfigBuilder = new TemplateConfiguration.Builder();
    }

    public static FastAutoGenerator create(@NotNull String url, String username, String password) {
        return new FastAutoGenerator(new DataSourceConfig.Builder(url, username, password));
    }

    public static FastAutoGenerator create(@NotNull DataSourceConfig.Builder dataSourceConfigBuilder) {
        return new FastAutoGenerator(dataSourceConfigBuilder);
    }

    /**
     * 读取控制台输入内容
     */
    private final Scanner scanner = new Scanner(System.in);

    /**
     * 控制台输入内容读取并打印提示信息
     * @param message 提示信息
     * @return String
     */
    public String scannerNext(String message) {
        System.out.println(message);
        String nextLine = scanner.nextLine();
        if (StringUtils.isBlank(nextLine)) {
            // 如果输入空行继续等待
            return scanner.next();
        }
        return nextLine;
    }

    /**
     * 全局配置
     * @param consumer 自定义全局配置
     * @return FastAutoGenerator
     */
    public FastAutoGenerator globalConfig(Consumer<ProjectConfiguration.Builder> consumer) {
        consumer.accept(this.globalConfigBuilder);
        return this;
    }

    /**
     * 包配置
     * @param consumer 自定义包配置
     * @return FastAutoGenerator
     */
    public FastAutoGenerator packageConfig(Consumer<PackageConfiguration.Builder> consumer) {
        consumer.accept(this.packageConfigBuilder);
        return this;
    }

    /**
     * 策略配置
     * @param consumer 自定义策略配置
     * @return FastAutoGenerator
     */
    public FastAutoGenerator strategyConfig(Consumer<StrategyConfig.Builder> consumer) {
        consumer.accept(this.strategyConfigBuilder);
        return this;
    }

    public FastAutoGenerator strategyConfig(BiConsumer<Function<String, String>, StrategyConfig.Builder> biConsumer) {
        biConsumer.accept(this::scannerNext, this.strategyConfigBuilder);
        return this;
    }

    /**
     * 模板引擎配置
     * @param templateEngine 模板引擎
     * @return FastAutoGenerator
     */
    public FastAutoGenerator templateEngine(AbstractTemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
        return this;
    }

    /**
     * 开始生成代码
     */
    public void execute() {
        AutoGenerator generator = new AutoGenerator(this.dataSourceConfigBuilder.build(), this.injectionConfigBuilder.build(), this.packageConfigBuilder.build(), this.strategyConfigBuilder.build());
        generator.global(this.globalConfigBuilder.build()); // 全局配置
        generator.template(this.templateConfigBuilder.build()); // 模板配置
        // 执行
        generator.execute(this.templateEngine);
    }
}
