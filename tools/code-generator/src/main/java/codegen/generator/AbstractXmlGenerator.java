package codegen.generator;

import codegen.GeneratedFile;
import codegen.IntrospectedTable;
import codegen.xml.Document;

import java.util.List;

public abstract class AbstractXmlGenerator extends AbstractTableIntrospector {

    public abstract Document getDocument();

    @Override
    public List<GeneratedFile> introspect(IntrospectedTable table) {
        return null;
    }
}