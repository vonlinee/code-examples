package codegen.generator;

import codegen.GeneratedFile;
import codegen.IntrospectedTable;

import java.util.ArrayList;
import java.util.List;

public class JavaPoetCodeGenerator extends AbstractTableIntrospector {
    @Override
    public List<GeneratedFile> introspect(IntrospectedTable table) {
        return new ArrayList<>();
    }
}
