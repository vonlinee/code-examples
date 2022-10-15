package io.devpl.codegen.core.meta.generator;

import io.devpl.codegen.core.meta.GeneratedFile;
import io.devpl.codegen.core.meta.IntrospectedTable;
import io.devpl.codegen.core.meta.xml.Document;

import java.util.List;

public abstract class AbstractXmlGenerator extends AbstractTableIntrospector {

    public abstract Document getDocument();

    @Override
    public List<GeneratedFile> introspect(IntrospectedTable table) {
        return null;
    }
}