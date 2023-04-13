package io.devpl.codegen.mbpg.template.impl;

import io.devpl.codegen.mbpg.config.po.IntrospectedTable;
import io.devpl.codegen.mbpg.template.TemplateArguments;

import java.util.Map;

/**
 * 动态模板参数，适用于自定义模板
 */
public class DynamicTemplateArguments extends TemplateArguments {

    public void addArgument(String name, Object value) {
        this.setProperty(name, value);
    }

    @Override
    public Map<String, Object> calculateArgumentsMap(IntrospectedTable tableInfo) {
        return this.getProperties();
    }
}
