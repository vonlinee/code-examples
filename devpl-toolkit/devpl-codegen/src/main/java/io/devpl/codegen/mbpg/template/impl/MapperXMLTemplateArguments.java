package io.devpl.codegen.mbpg.template.impl;

import io.devpl.codegen.mbpg.config.po.IntrospectedTable;
import io.devpl.codegen.mbpg.template.TemplateArguments;

import java.util.Collections;
import java.util.Map;

public class MapperXMLTemplateArguments extends TemplateArguments {

    @Override
    public Map<String, Object> calculateArgumentsMap(IntrospectedTable tableInfo) {
        return Collections.emptyMap();
    }
}
