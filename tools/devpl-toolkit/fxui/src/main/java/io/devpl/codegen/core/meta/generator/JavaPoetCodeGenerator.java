package io.devpl.codegen.core.meta.generator;

import io.devpl.codegen.core.meta.GeneratedFile;
import io.devpl.codegen.core.meta.IntrospectedTable;

import java.util.ArrayList;
import java.util.List;

public class JavaPoetCodeGenerator extends AbstractTableIntrospector {
    @Override
    public List<GeneratedFile> introspect(IntrospectedTable table) {
        return new ArrayList<>();
    }
}
