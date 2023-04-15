package io.devpl.codegen.api.gen.template.impl;

import io.devpl.codegen.mbpg.ConverterFileName;
import io.devpl.codegen.mbpg.config.BaseBuilder;
import io.devpl.codegen.mbpg.config.ConstVal;
import io.devpl.codegen.mbpg.config.StrategyConfig;
import io.devpl.codegen.utils.ClassUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class ServiceTemplateArguments extends TemplateArgumentsMap {

    private final static Logger LOGGER = LoggerFactory.getLogger(ServiceTemplateArguments.class);

    /**
     * 自定义继承的Service类全称，带包名
     */
    private String superServiceClass = "com.baomidou.mybatisplus.extension.service.IService";

    /**
     * 自定义继承的ServiceImpl类全称，带包名
     */
    private String superServiceImplClass = "com.baomidou.mybatisplus.extension.service.impl.ServiceImpl";

    public String getSuperServiceClass() {
        return superServiceClass;
    }

    public String getSuperServiceImplClass() {
        return superServiceImplClass;
    }

    /**
     * 转换输出Service文件名称
     * @since 3.5.0
     */
    private ConverterFileName converterServiceFileName = (entityName -> "I" + entityName + ConstVal.SERVICE);

    /**
     * 转换输出ServiceImpl文件名称
     * @since 3.5.0
     */
    private ConverterFileName converterServiceImplFileName = (entityName -> entityName + ConstVal.SERVICE_IMPL);

    /**
     * 是否覆盖已有文件（默认 false）
     * @since 3.5.2
     */
    private boolean fileOverride;


    public ConverterFileName getConverterServiceFileName() {
        return converterServiceFileName;
    }


    public ConverterFileName getConverterServiceImplFileName() {
        return converterServiceImplFileName;
    }

    public boolean isFileOverride() {
        return fileOverride;
    }

    @Override
    public Map<String, Object> asMap() {
        Map<String, Object> data = new HashMap<>();
        data.put("superServiceClassPackage", this.superServiceClass);
        data.put("superServiceClass", ClassUtils.getSimpleName(this.superServiceClass));
        data.put("superServiceImplClassPackage", this.superServiceImplClass);
        data.put("superServiceImplClass", ClassUtils.getSimpleName(this.superServiceImplClass));
        return data;
    }

    public static class Builder extends BaseBuilder {

        private final ServiceTemplateArguments service = new ServiceTemplateArguments();

        public Builder(StrategyConfig strategyConfig) {
            super(strategyConfig);
        }

        /**
         * Service接口父类
         * @param clazz 类
         * @return this
         */
        public Builder superServiceClass(Class<?> clazz) {
            return superServiceClass(clazz.getName());
        }

        /**
         * Service接口父类
         * @param superServiceClass 类名
         * @return this
         */
        public Builder superServiceClass(String superServiceClass) {
            this.service.superServiceClass = superServiceClass;
            return this;
        }

        /**
         * Service实现类父类
         * @param clazz 类
         * @return this
         */
        public Builder superServiceImplClass(Class<?> clazz) {
            return superServiceImplClass(clazz.getName());
        }

        /**
         * Service实现类父类
         * @param superServiceImplClass 类名
         * @return this
         */
        public Builder superServiceImplClass(String superServiceImplClass) {
            this.service.superServiceImplClass = superServiceImplClass;
            return this;
        }

        /**
         * 转换输出service接口文件名称
         * @param converter 　转换处理
         * @return this
         * @since 3.5.0
         */
        public Builder convertServiceFileName(ConverterFileName converter) {
            this.service.converterServiceFileName = converter;
            return this;
        }

        /**
         * 转换输出service实现类文件名称
         * @param converter 　转换处理
         * @return this
         * @since 3.5.0
         */
        public Builder convertServiceImplFileName(ConverterFileName converter) {
            this.service.converterServiceImplFileName = converter;
            return this;
        }

        /**
         * 格式化service接口文件名称
         * @param format 　格式
         * @return this
         * @since 3.5.0
         */
        public Builder formatServiceFileName(String format) {
            return convertServiceFileName((entityName) -> String.format(format, entityName));
        }

        /**
         * 格式化service实现类文件名称
         * @param format 　格式
         * @return this
         * @since 3.5.0
         */
        public Builder formatServiceImplFileName(String format) {
            return convertServiceImplFileName((entityName) -> String.format(format, entityName));
        }

        /**
         * 覆盖已有文件（该方法后续会删除，替代方法为enableFileOverride方法）
         * @see #enableFileOverride()
         */
        @Deprecated
        public Builder fileOverride() {
            LOGGER.warn("fileOverride方法后续会删除，替代方法为enableFileOverride方法");
            this.service.fileOverride = true;
            return this;
        }

        /**
         * 覆盖已有文件
         */
        public Builder enableFileOverride() {
            this.service.fileOverride = true;
            return this;
        }


        public ServiceTemplateArguments get() {
            return this.service;
        }
    }
}
