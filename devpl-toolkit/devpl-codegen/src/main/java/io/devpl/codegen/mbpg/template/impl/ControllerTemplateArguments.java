package io.devpl.codegen.mbpg.template.impl;

import io.devpl.codegen.mbpg.config.builder.BaseBuilder;
import io.devpl.codegen.mbpg.config.ConstVal;
import io.devpl.codegen.mbpg.config.StrategyConfig;
import io.devpl.codegen.mbpg.config.po.TableInfo;
import io.devpl.codegen.mbpg.function.ConverterFileName;
import io.devpl.codegen.mbpg.template.TemplateArguments;
import io.devpl.codegen.mbpg.util.ClassUtils;
import io.devpl.codegen.mbpg.util.StringUtils;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * 控制器属性配置
 */
public class ControllerTemplateArguments extends TemplateArguments {

    private final static Logger LOGGER = LoggerFactory.getLogger(ControllerTemplateArguments.class);

    private ControllerTemplateArguments() {
    }

    /**
     * 生成 <code>@RestController</code> 控制器（默认 false）
     * <pre>
     *      <code>@Controller</code> -> <code>@RestController</code>
     * </pre>
     */
    private boolean restStyle;

    /**
     * 驼峰转连字符（默认 false）
     * <pre>
     *      <code>@RequestMapping("/managerUserActionHistory")</code> -> <code>@RequestMapping("/manager-user-action-history")</code>
     * </pre>
     */
    private boolean hyphenStyle;

    /**
     * 自定义继承的Controller类全称，带包名
     */
    private String superClass;

    /**
     * 转换输出控制器文件名称
     *
     * @since 3.5.0
     */
    private ConverterFileName converterFileName = (entityName -> entityName + ConstVal.CONTROLLER);

    /**
     * 是否覆盖已有文件（默认 false）
     *
     * @since 3.5.2
     */
    private boolean fileOverride;

    public boolean isRestStyle() {
        return restStyle;
    }

    public boolean isHyphenStyle() {
        return hyphenStyle;
    }

    @Nullable
    public String getSuperClass() {
        return superClass;
    }

    public ConverterFileName getConverterFileName() {
        return converterFileName;
    }

    public boolean isFileOverride() {
        return fileOverride;
    }

    @Override
    public Map<String, Object> initialize(TableInfo tableInfo) {
        Map<String, Object> data = new HashMap<>(5);
        data.put("controllerMappingHyphen", StringUtils.camelToHyphen(tableInfo.getEntityPath()));
        data.put("controllerMappingHyphenStyle", this.hyphenStyle);
        data.put("restControllerStyle", this.restStyle);
        data.put("superControllerClassPackage", StringUtils.isBlank(superClass) ? null : superClass);
        data.put("superControllerClass", ClassUtils.getSimpleName(this.superClass));
        return data;
    }

    public static class Builder extends BaseBuilder {

        private final ControllerTemplateArguments controller = new ControllerTemplateArguments();

        public Builder(StrategyConfig strategyConfig) {
            super(strategyConfig);
        }

        /**
         * 父类控制器
         *
         * @param clazz 父类控制器
         * @return this
         */
        public Builder superClass(Class<?> clazz) {
            return superClass(clazz.getName());
        }

        /**
         * 父类控制器
         *
         * @param superClass 父类控制器类名
         * @return this
         */
        public Builder superClass(String superClass) {
            this.controller.superClass = superClass;
            return this;
        }

        /**
         * 开启驼峰转连字符
         *
         * @return this
         * @since 3.5.0
         */
        public Builder enableHyphenStyle() {
            this.controller.hyphenStyle = true;
            return this;
        }

        /**
         * 开启生成@RestController控制器
         *
         * @return this
         * @since 3.5.0
         */
        public Builder enableRestStyle() {
            this.controller.restStyle = true;
            return this;
        }

        /**
         * 转换输出文件名称
         *
         * @param converter 　转换处理
         * @return this
         * @since 3.5.0
         */
        public Builder convertFileName(ConverterFileName converter) {
            this.controller.converterFileName = converter;
            return this;
        }

        /**
         * 格式化文件名称
         *
         * @param format 　格式
         * @return this
         * @since 3.5.0
         */
        public Builder formatFileName(String format) {
            return convertFileName((entityName) -> String.format(format, entityName));
        }

        /**
         * 覆盖已有文件（该方法后续会删除，替代方法为enableFileOverride方法）
         *
         * @see #enableFileOverride()
         */
        @Deprecated
        public Builder fileOverride() {
            LOGGER.warn("fileOverride方法后续会删除，替代方法为enableFileOverride方法");
            this.controller.fileOverride = true;
            return this;
        }

        /**
         * 覆盖已有文件
         *
         * @since 3.5.3
         */
        public Builder enableFileOverride() {
            this.controller.fileOverride = true;
            return this;
        }


        public ControllerTemplateArguments get() {
            return this.controller;
        }
    }
}
