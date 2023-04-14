package io.devpl.codegen.generator.template.impl;

import io.devpl.codegen.api.IntrospectedTable;
import io.devpl.codegen.generator.template.TemplateArguments;

import java.util.Collections;
import java.util.Map;

public class MapperXMLTemplateArguments extends TemplateArguments {

    @Override
    public Map<String, Object> calculateArgumentsMap(IntrospectedTable tableInfo) {
        return Collections.emptyMap();
    }
}
